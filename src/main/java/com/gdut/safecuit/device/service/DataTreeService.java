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

	/*@Resource
	private AsyncTask asyncTask;
	@Resource
	private DataTreeMapper dataTreeMapper;
	@Resource
	private DeviceEventMapper deviceEventMapper;

	*/@Resource
	private AreaMapper areaMapper;
	@Resource
	private OrganizationMapper organizationMapper;
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
		//判断省结点是否为空
		Boolean proIsEmpty = false;
		//判断市结点是否为空
		Boolean cityIsEmpty = false;

		Organization organization = organizationMapper.selectByPrimaryKey(orgId);
		Area area = areaMapper.selectByPrimaryKey(organization.getAreaId());
		City city = provinceCityAreaService.getCity(area.getFather());
		Province province = provinceCityAreaService.getProvince(city.getFather());

		Integer id = uniqueMainKeyMapper.getMainKey();//数据库获取全局唯一的id

		DataTree provinceDataTree = dataTreeMapper.selectByName(province.getProvince());
		DataTree cityDataTree = dataTreeMapper.selectByName(city.getCity());
		DataTree areaDataTree = dataTreeMapper.selectByName(area.getArea());

		//先查看是否存在数据，再决定是否将数据插入树中
		if(provinceDataTree == null){
			insertNode(new DataTree(id+1 ,province.getProvince() ,-1 ,null));

			proIsEmpty = true;
		}

		if(cityDataTree == null){
			//如果上面添加了省结点，添加市结点要从id+2开始设置结点id；
			//如果省结点已存在，则从id+1开始设置结点id
			insertNodeJudge(id+2 ,city.getCity() ,id+1 ,proIsEmpty);

			cityIsEmpty = true;
		}

		if(areaDataTree == null){

			if (cityIsEmpty)
				//如果上面添加了省结点和市结点，则添加区结点要从id+3开始设置结点id；
				//如果上面只添加了市结点，则添加区结点要从id+2开始设置结点id
				insertNodeJudge(id+3 ,area.getArea() ,id+2 ,proIsEmpty);
			else
				//如果上面只添加了省结点，则添加区结点要从id+2开始设置结点id
				//如果都没有添加，则添加区结点要从id+1开始设置结点id
				insertNodeJudge(id+2 ,area.getArea() ,cityDataTree.getId() ,proIsEmpty);
		}

		organization.setParentId(uniqueMainKeyMapper.getMainKey());
		organizationMapper.updateByPrimaryKeySelective(organization);

	}

	private void insertNodeJudge(Integer id ,String name ,Integer parentId ,Boolean proIsEmpty){
		//如果省结点为空
		if (proIsEmpty)
			insertNode(new DataTree(id ,name ,parentId ,null));
		//如果省结点不为空
		else
			insertNode(new DataTree(id-1 ,name ,parentId-1 ,null));
	}

	private void insertNode(DataTree dataTree){
		dataTreeMapper.insertSelective(dataTree);
		uniqueMainKeyMapper.updateMainKey(dataTree.getId() ,dataTree.getId()-1);
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

		List<DataTreeVO> dataTreeVOS = getDataTree(parentId);

		if(dataTreeVOS == null)
			return null;

		for (DataTreeVO dataTreeVO:dataTreeVOS) {

			//如果树的类型为最底层是区(treeType=1)的话，判断orgId是否为空，不为空则不再遍历下面的孩子
			if (treeType == ORG_TREE_TYPE)
				if (dataTreeVO.getOrgId() != null)
					return null;

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

	private List<DataTreeVO> getDataTree(Integer parentId){
		List<DataTreeVO> dataTreeVOS = new ArrayList<>();
		List<DataTree> groupDataTrees = dataTreeMapper.selectByParentId(parentId);
		List<Organization> organizations = organizationService.selectOrganizationByParentId(parentId);
		List<ElectricBox> electricBoxs = electricBoxMapper.selectByParentId(parentId);

		for (DataTree groupDataTree : groupDataTrees) {
			System.out.println(groupDataTrees);
			DataTreeVO dataTreeVO = new DataTreeVO(groupDataTree.getId() ,groupDataTree.getName()
					, GROUP_TYPE ,parentId ,null);

			dataTreeVOS.add(dataTreeVO);
		}

		for (Organization organization : organizations) {
			System.out.println(organizations);
			DataTreeVO dataTreeVO = new DataTreeVO(organization.getOrgId() ,organization.getName()
					, ORG_TYPE ,organization.getParentId() ,organization.getOrgId());

			dataTreeVOS.add(dataTreeVO);
		}

		for (ElectricBox electricBox : electricBoxs) {
			System.out.println(electricBoxs);
			DataTreeVO dataTreeVO = new DataTreeVO(electricBox.getId() ,electricBox.getName()
					, ELECTRIC_BOX_TYPE, parentId ,electricBox.getOrgId());

			dataTreeVOS.add(dataTreeVO);
		}
		return dataTreeVOS;
	}

	/**
	 * 修改某结点的父母结点（拖动该结点或该名称）,如果某机构下的结点被移动到机构外(传进来的父母结点的orgId为null)则返回并提示错误
	 * @param id 要修改的节点id
	 * @param name 要修改的节点名称
	 * @param parentId 要修改的节点父母节点id
	 * @param orgId 要修改的父母节点的orgId
	 * @param typeId 要修改的节点类型
	 * @return 修改数
	 */
	public int update(Integer id ,String name ,Integer parentId ,Integer orgId ,Integer typeId){
		Integer update;
		DataTree dataTree;
		Organization organization;
		ElectricBox electricBox;

		//如果要修改的节点父母节点的orgId为空，则证明某机构下的结点被移动到机构外
		if (orgId == null)
			return -2;

		if (typeId == GROUP_TYPE){
			dataTree = dataTreeMapper.selectByPrimaryKey(id);
			dataTree.setName(name);
			dataTree.setOrgId(orgId);
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
			electricBox.setOrgId(orgId);
			electricBox.setParentId(parentId);
			update = electricBoxMapper.updateByPrimaryKeySelective(electricBox);
		}

		//异步更新缓存
		//asyncTask.updateDataTreeCache(DEVICE_TYPE);
		return update;
	}


	@Override
	public BaseDao<DataTree> getDao() {
		return dataTreeMapper;
	}
}
