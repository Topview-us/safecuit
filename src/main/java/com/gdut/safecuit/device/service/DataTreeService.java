package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.UniqueMainKeyMapper;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.common.po.DataTree;
import com.gdut.safecuit.device.common.po.Device;
import com.gdut.safecuit.device.common.po.ElectricBox;
import com.gdut.safecuit.device.common.vo.DataTreeVO;
import com.gdut.safecuit.device.dao.DataTreeMapper;
import com.gdut.safecuit.device.dao.DeviceMapper;
import com.gdut.safecuit.device.dao.ElectricBoxMapper;
import com.gdut.safecuit.monitor.common.EventCode;
import com.gdut.safecuit.monitor.dao.DeviceEventMapper;
import com.gdut.safecuit.organization.common.po.Area;
import com.gdut.safecuit.organization.common.po.City;
import com.gdut.safecuit.organization.common.po.Organization;
import com.gdut.safecuit.organization.common.po.Province;
import com.gdut.safecuit.organization.dao.AreaMapper;
import com.gdut.safecuit.organization.dao.OrganizationMapper;
import com.gdut.safecuit.organization.service.OrganizationService;
import com.gdut.safecuit.organization.service.ProvinceCityAreaService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.gdut.safecuit.device.common.DataTreeType.*;

/**
 * Created by Garson in 21:07 2018/1/20
 * Description :
 */
@Service
public class DataTreeService extends BaseServiceImpl<DataTree> {
	@Resource
	private UniqueMainKeyMapper uniqueMainKeyMapper;
	@Resource
	private DataTreeMapper dataTreeMapper;
	@Resource
	private OrganizationMapper organizationMapper;
	@Resource
	private AreaMapper areaMapper;
	@Resource
	private ProvinceCityAreaService provinceCityAreaService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private DeviceMapper deviceMapper;
	@Resource
	private ElectricBoxMapper electricBoxMapper;
	@Resource
	private DeviceEventMapper deviceEventMapper;

	/**
	 * 添加“分组”的方法，逻辑是先判断权限，如果是超管则可随意添加，如果是机构管理员则只能添加自己机构的结点，实现的方法是：
	 * 判断机构管理员所属的机构id与传进来的机构id是否相同，如果是则证明在自己机构下添加分组的
	 *
	 * @param name 分组名
 	 * @param parentId 父母结点id
	 * @param orgId	所属机构id（可能为空） ，该机构id由前端获取父母结点的orgId传入
	 * @return 添加数
	 */
	@Transactional
	public int insertGroup(String name ,Integer parentId ,Integer orgId){


		//判断机构管理员所属的机构id与传进来的机构id是否相同，是则证明实在自己机构下添加分组的


		//判断是超管还是机构管理员，如果是机构管理员则判断orgId是否为空，不为空则表示机构管理员在自己机构下添加分组，继续执行，否则返回错误信息
		/*DataTree dataTree = dataTreeMapper.selectByParentIdInGroup(parentId);
		while (dataTree.getOrgId() != null && dataTree.getParentId() != -1){
			dataTree = dataTreeMapper.selectByParentIdInGroup(dataTree.getParentId());
		}
		if (dataTree.getOrgId() == null)
			return -3;*/

		Integer id = uniqueMainKeyMapper.getMainKey();//数据库获取全局唯一的id
		uniqueMainKeyMapper.updateMainKey(id+1 ,id);//更新此id

		DataTree dataTree = new DataTree();
		dataTree.setId(id+1);
		dataTree.setParentId(parentId);
		dataTree.setName(name);
		dataTree.setOrgId(orgId);

		return dataTreeMapper.insertSelective(dataTree);
	}

	@Async
	@Transactional
	public void insertOrg(Integer orgId){

		Organization organization = organizationMapper.selectByPrimaryKey(orgId);
		Area area = areaMapper.selectByPrimaryKey(organization.getAreaId());
		//Area area = provinceCityAreaService.getArea(String.valueOf(organization.getAreaId()));
		City city = provinceCityAreaService.getCity(area.getFather());
		Province province = provinceCityAreaService.getProvince(city.getFather());

		Integer id = uniqueMainKeyMapper.getMainKey();//数据库获取全局唯一的id,该值已用
		Integer oldId = id;//记录未修改前的全局id

		DataTree provinceDataTree = dataTreeMapper.selectByNameAndParentId(province.getProvince() ,-1);
		DataTree cityDataTree;
		DataTree areaDataTree;

		//先查看是否存在数据，再决定是否将数据插入树中
		if(provinceDataTree == null){

			id = insertNode(new DataTree(id+1 ,province.getProvince() ,-1 ,null) ,id);
			id = insertNode(new DataTree(id+1 ,city.getCity() ,id ,null) ,id);
			id = insertNode(new DataTree(id+1 ,area.getArea() ,id ,null) ,id);
			uniqueMainKeyMapper.updateMainKey(id ,oldId);
			organization.setParentId(id);

		} else {

			cityDataTree = dataTreeMapper.selectByNameAndParentId(city.getCity() ,provinceDataTree.getId());
			if (cityDataTree == null){
				id = insertNode(new DataTree(id+1 ,city.getCity() ,provinceDataTree.getId() ,null) ,id);
				id = insertNode(new DataTree(id+1 ,area.getArea() ,id ,null) ,id);
				uniqueMainKeyMapper.updateMainKey(id ,oldId);
				organization.setParentId(id);
			}else {
				areaDataTree = dataTreeMapper.selectByNameAndParentId(area.getArea() ,cityDataTree.getId());
				if (areaDataTree == null){
					id = insertNode(new DataTree(id+1 ,area.getArea() ,cityDataTree.getId() ,null) ,id);
					uniqueMainKeyMapper.updateMainKey(id ,oldId);
					organization.setParentId(id);
				}else
					organization.setParentId(areaDataTree.getId());
			}
		}

		organizationMapper.updateByPrimaryKeySelective(organization);

	}

	private int insertNode(DataTree dataTree ,int id){
		dataTreeMapper.insertSelective(dataTree);
		id++;
		return id;
	}

	@Transactional
	public Integer deleteData(Integer groupId){
		//当分组结点在分组表、电箱表、机构表都没有孩子结点时才能删除，否则提示先删除分组下的孩子
		if (dataTreeMapper.hasChild(groupId ,GROUP_TYPE) != null
				|| dataTreeMapper.hasChild(groupId ,ORG_TYPE) != null
				|| dataTreeMapper.hasChild(groupId ,ELECTRIC_BOX_TYPE) != null)
			return -1;
		else
			return dataTreeMapper.deleteByPrimaryKey(groupId);
	}

	/**
	 * 根据parentId获取树
	 * @param parentId 父母结点id
	 * @param treeType 树的类型，1为最底层是区的树，2为最底层是电箱的树
	 * @return 树
	 */
	public List<DataTreeVO> showTree(Integer parentId ,Integer treeType){
		List<Device> devices = new ArrayList<>();
		List<Integer> deviceIds = deviceEventMapper.selectDeviceIdByType(EventCode.UNSOLVED);//获取未处理报警的设备对应的电箱id
		System.out.println(deviceIds);
		for (Integer deviceId : deviceIds) {
			Device device = deviceMapper.selectByPrimaryKey(deviceId);
			devices.add(device);
		}

		//CopyOnWriteArrayList,线程安全的集合类
		CopyOnWriteArrayList<Device> COWElectricIds = new CopyOnWriteArrayList<>(devices);

		//Integer parentId ==
		//带判断权限后设置parentId
		return getDataTreeList(parentId ,treeType ,COWElectricIds);
	}

	private List<DataTreeVO> getDataTreeList(Integer parentId, Integer treeType
			,CopyOnWriteArrayList<Device> copyOnWriteArrayList){

		List<DataTreeVO> dataTreeVOS = getDataTree(parentId ,treeType);

		if(dataTreeVOS == null || dataTreeVOS.size() == 0)
			return null;

		for (DataTreeVO dataTreeVO:dataTreeVOS) {

			//如果树的类型为最底层是区(treeType=1)的话，判断orgId是否为空，不为空则不再遍历下面的孩子
			/*if (treeType == ORG_TREE_TYPE)
				if (dataTreeVO.getOrgId() != null)
					return null;*/

			//报警设备对应的电箱结点显红色的判断
			if (dataTreeVO.getTypeId() == ELECTRIC_BOX_TYPE)
				if (copyOnWriteArrayList.size() != 0)
					for (Device device : copyOnWriteArrayList)
						//如果结点的id与未处理报警的设备对应的电箱id相同的话，就将该结点的报警状态设为1
						if (device.getElectricBoxId().intValue() == dataTreeVO.getId()){
							dataTreeVO.setIsHint(EventCode.IS_WARNING);
							copyOnWriteArrayList.remove(device);//移除该设备
						}

			dataTreeVO.setChildren(getDataTreeList(dataTreeVO.getId() ,treeType ,copyOnWriteArrayList));
		}
		return dataTreeVOS;
	}

	private List<DataTreeVO> getDataTree(Integer parentId ,Integer treeType){
		List<DataTreeVO> dataTreeVOS = new ArrayList<>();
		List<DataTree> groupDataTrees = dataTreeMapper.selectByParentId(parentId);
		List<Organization> organizations = organizationService.selectOrganizationByParentId(parentId);
		List<ElectricBox> electricBoxes = electricBoxMapper.selectByParentId(parentId);

		for (DataTree groupDataTree : groupDataTrees) {
			//System.out.println(groupDataTree);
			DataTreeVO dataTreeVO = new DataTreeVO(groupDataTree.getId() ,groupDataTree.getName()
					, GROUP_TYPE ,parentId ,groupDataTree.getOrgId());

			dataTreeVOS.add(dataTreeVO);
		}

		if (treeType == ORG_TREE_TYPE)
			return dataTreeVOS;

		for (Organization organization : organizations) {
			//System.out.println(organization);
			DataTreeVO dataTreeVO = new DataTreeVO(organization.getOrgId() ,organization.getName()
					, ORG_TYPE ,organization.getParentId() ,organization.getOrgId());

			dataTreeVOS.add(dataTreeVO);
		}

		for (ElectricBox electricBox : electricBoxes) {
			//System.out.println(electricBox);
			DataTreeVO dataTreeVO = new DataTreeVO(electricBox.getId() ,electricBox.getName()
					, ELECTRIC_BOX_TYPE ,parentId ,electricBox.getOrgId());

			dataTreeVOS.add(dataTreeVO);
		}
		return dataTreeVOS;
	}

	/*public List<DataTreeVO> selectByFuzzyQuery(String name){
		List<DataTreeVO> dataTreeVOS = new ArrayList<>();
		List<DataTree> dataTrees = dataTreeMapper.selectGroupByFuzzyQuery(name);
	//	List<Organization> organizations = dataTreeMapper.selectOrganizationByFuzzyQuery(name);
		List<ElectricBox> electricBoxes = electricBoxMapper.selectElectricBoxByFuzzyQuery(name);

		for (DataTree groupDataTree : dataTrees) {
			DataTreeVO dataTreeVO = new DataTreeVO(groupDataTree.getId() ,groupDataTree.getName()
					, GROUP_TYPE ,groupDataTree.getParentId() ,groupDataTree.getOrgId());

			dataTreeVOS.add(dataTreeVO);
		}

	*//*	for (Organization organization : organizations) {
			DataTreeVO dataTreeVO = new DataTreeVO(organization.getOrgId() ,organization.getName()
					, ORG_TYPE ,organization.getParentId() ,organization.getOrgId());

			dataTreeVOS.add(dataTreeVO);
		}*//*

		for (ElectricBox electricBox : electricBoxes) {
			DataTreeVO dataTreeVO = new DataTreeVO(electricBox.getId() ,electricBox.getName()
					, ELECTRIC_BOX_TYPE ,electricBox.getParentId() ,electricBox.getOrgId());

			dataTreeVOS.add(dataTreeVO);
		}
		return dataTreeVOS;
	}*/


	/**
	 * 修改某结点的父母结点（拖动该结点或该名称）,如果某机构下的结点被移动到机构外(传进来的父母结点的orgId为null)则返回并提示错误
	 * @param id 要修改的节点id
	 * @param name 要修改的节点名称
	 * @param parentId 目的结点的id
	 * @param orgId 要移动的结点的orgId
	 * @param parentOrgId 目的结点的orgId
	 * @param typeId 要修改的节点类型
	 * @return 修改数
	 */
	public int update(Integer id ,String name
				,Integer parentId ,Integer orgId
				,Integer parentOrgId ,Integer typeId){

		Integer update;
		DataTree dataTree;
		Organization organization;
		ElectricBox electricBox;

		//如果移动的是机构内的结点（不包括机构结点）
		if (orgId != null && typeId != ORG_TYPE)
			//如果目标结点的orgId与自身orgId不同，证明移动到了所属机构外,返回错误信息
			if (parentOrgId == null || parentOrgId.intValue() != orgId)
				return -2;

		//如果目标结点选了自己或自己的孩子结点，则返回并提示错误
		if (parentId.intValue() == id || !judge(parentId ,id))
			return -3;

		if (typeId == GROUP_TYPE){
			dataTree = dataTreeMapper.selectByPrimaryKey(id);
			dataTree.setName(name);
		//	dataTree.setOrgId(orgId);
			dataTree.setParentId(parentId);
			update = dataTreeMapper.updateByPrimaryKey(dataTree);
		}
		else if (typeId == ORG_TYPE){
			organization = organizationMapper.selectByPrimaryKey(id);
			organization.setName(name);
			organization.setParentId(parentId);
			update = organizationMapper.updateByPrimaryKeySelective(organization);
		}
		else {
			electricBox = electricBoxMapper.selectByPrimaryKey(id);
			electricBox.setName(name);
		//	electricBox.setOrgId(orgId);
			electricBox.setParentId(parentId);
			update = electricBoxMapper.updateByPrimaryKeySelective(electricBox);
		}

		return update;
	}

	/**
	 * 判断结点移动是否移动到了自己的孩子内
	 * @param id 一开始传进来的id为目标结点的id
	 * @param updatedId 被修改结点的id
	 * @return 移动结果
	 */
	private Boolean judge(Integer id ,Integer updatedId){

		Integer dataTreeParentId = dataTreeMapper.selectParentIdById(id ,GROUP_TYPE);
		if (dataTreeParentId == null){
			Integer orgParentId = dataTreeMapper.selectParentIdById(id ,ORG_TYPE);
			if (orgParentId == null) {
				Integer ebParentId = dataTreeMapper.selectParentIdById(id, ELECTRIC_BOX_TYPE);
				if (ebParentId.intValue() == updatedId)
					return false;
				else
					return judge(ebParentId ,updatedId);
			}
			else if (orgParentId.intValue() == updatedId)
				return false;
			else
				return judge(orgParentId ,updatedId);
		}
		else if (dataTreeParentId.intValue() == updatedId)
			return false;
		else if (dataTreeParentId == -1)
			return true;
		else
			return judge(dataTreeParentId ,updatedId);

	}

	@Override
	public BaseDao<DataTree> getDao() {
		return dataTreeMapper;
	}
}
