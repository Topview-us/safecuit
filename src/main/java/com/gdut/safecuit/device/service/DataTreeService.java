package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.UniqueMainKeyMapper;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.common.po.DataTree;
import com.gdut.safecuit.device.dao.DataTreeMapper;
import com.gdut.safecuit.organization.common.po.Area;
import com.gdut.safecuit.organization.common.po.City;
import com.gdut.safecuit.organization.common.po.Province;
import com.gdut.safecuit.organization.dao.AreaMapper;
import com.gdut.safecuit.organization.dao.CityMapper;
import com.gdut.safecuit.organization.dao.ProvinceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static com.gdut.safecuit.common.DataTreeTypeCode.*;

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
	private CityMapper cityMapper;
	@Resource
	private ProvinceMapper provinceMapper;
	/*@Resource
	private DeviceMapper deviceMapper;
	@Resource
	private ElectricBoxMapper electricBoxMapper;*//*

	*/

	/**
	 * 添加“分组”的方法，逻辑是先判断权限，如果是超管则可随意添加，如果是机构管理员则只能添加自己机构的结点，实现的方法是：
	 * 判断传进来的orgId是否为空，如果为空，证明是机构上的结点，返回错误信息；如果不为空，则证明他在自己机构内操作，然后在分组表中插入数据
	 * @param name 分组名
 	 * @param parentId 父母结点id
	 * @param orgId	所属机构id（可能为空）
	 * @return 添加数
	 */
	@Transactional
	public int insertGroup(String name ,Integer parentId ,Integer orgId){

		//判断是超管还是机构管理员，如果是机构管理员则判断orgId是否为空，不为空则表示机构管理员在自己机构下添加分组，继续执行，否则返回错误信息
	/*	DataTree dataTree = dataTreeMapper.selectOrgIdAndParentIdByParentIdInGroup(parentId);
		while (dataTree.getOrgId() != null){
			dataTree = dataTreeMapper.selectOrgIdAndParentIdByParentIdInGroup(dataTree.getParentId());
		}*/

		Integer id = uniqueMainKeyMapper.getMainKey();//数据库获取全局唯一的id
		uniqueMainKeyMapper.updateMainKey(id+1 ,id);//更新此id

		DataTree dataTree = new DataTree();
		dataTree.setId(id+1);
		dataTree.setParentId(parentId);
		dataTree.setOrgId(orgId);

		//判断是否在同级中有相同名字的分组
	//	if(dataTreeMapper.selectByNameAndParentId(dataTree.getName() ,dataTree.getParentId()) == 0)
	//		return -1;

		//判断该结点的上一级原先有没有孩子
//		if(dataTreeMapper.hasChild(dataTree.getParentId()) == 0)
//			dataTreeMapper.updateParentState(dataTree.getParentId());

		//异步更新缓存
		//asyncTask.updateDataTreeCache(DEVICE_TYPE);

		return dataTreeMapper.insertSelective(dataTree);
	}


	/*@Transactional
	public int insertOrg(Integer orgId){

		Integer insert;
		Area area = areaMapper.selectByPrimaryKey(orgId);
		List<City> city = cityMapper.getCityByProvinceId(area.getFather());
		Province province = provinceMapper.selectByPrimaryKey(city.getProvinceId());

		int lastDataId = dataTreeMapper.selectFinalDataId();//找出表中最后一条数据的id

		//先查看是否存在数据，再决定是否将数据插入树中
		if(dataTreeMapper.selectIdByName(province.getName()) != null)
			dataTreeMapper.insert(new DataTree(lastDataId+1 ,province.getName() ,1, "province" ,1,-1 ,0));
		if(dataTreeMapper.selectIdByName(city.getName()) != null)
			dataTreeMapper.insert(new DataTree(lastDataId+2 ,city.getName() ,2 ,"city" ,1 ,lastDataId+1 ,0));
		if(dataTreeMapper.selectIdByName(area.getName()) != null)
			dataTreeMapper.insert(new DataTree(lastDataId+3 ,area.getName() ,3 ,"area" ,1 ,lastDataId+2 ,0));

		//异步更新缓存
		asyncTask.updateDataTreeCache(ORG_TYPE);

		return insert;
	}*/


	/**
	 * 删除“机构”、“电箱”、“设备”的结点
	 * @param id 设备或电箱或机构的id
	 * @param typeId 设备或电箱或机构的结点类型id
	 *//*
	@Transactional
	public void deleteData(String id ,int typeId){

		int parentId = dataTreeMapper.selectParentIdByTypeInfo(id);
		DataTree dataTree = new DataTree(DataTreeTypeCode.DEVICE_TYPE ,id ,parentId);

		Integer childCount = dataTreeMapper.hasChild(dataTree.getParentId());
		//如果被删除结点的父母结点只有这个被删除的孩子，则将该父母结点的展开状态置为关
		if(childCount == 1)
			dataTreeMapper.updateByPrimaryKeySelective(new DataTree(dataTree.getParentId() ,CLOSE));

		//删除结点信息
		dataTreeMapper.deleteByTypeInfo(dataTree.getTypeInfo());

		//异步更新缓存
		asyncTask.updateDataTreeCache(typeId);

	}

	private List<DataTreeVO> getDataTree(Integer parentId ,Integer roleId){
		List<DataTreeVO> dataTreeVOS = new ArrayList<>();

		List<DataTree> dataTrees = dataTreeMapper.selectByParentId(parentId ,roleId);
		for (DataTree dataTree: dataTrees) {
			DataTreeVO dataTreeVO = new DataTreeVO(dataTree.getId() ,dataTree.getName() ,dataTree.getTypeId() ,dataTree.getTypeInfo() ,
					dataTree.getParentId() ,dataTree.getRoleId() ,0 ,dataTree.getStateId());

			dataTreeVOS.add(dataTreeVO);
		}
		return dataTreeVOS;
	}
	*//**
	 * 获取数据树数据，以树形结构输出
	 * @param parentId 父母结点id
	 * @return 数据树集合
	 *//*
	private List<DataTreeVO> getDataTreeList(Integer parentId ,Integer roleId
			,Integer typeId ,CopyOnWriteArrayList<String> copyOnWriteArrayList){

		List<DataTreeVO> dataTreeVOS = getDataTree(parentId ,roleId);

		if(dataTreeVOS == null)
			return null;

		for (DataTreeVO dataTreeVO:dataTreeVOS) {

			//报警设备对应的电箱结点显红色的判断
			if (copyOnWriteArrayList.size() != 0)
				for (String electricId:copyOnWriteArrayList)
					//如果结点信息与未处理报警的设备对应的电箱id相同的话，就将该结点的报警状态设为1
					if (electricId.equals(dataTreeVO.getTypeInfo())){
						dataTreeVO.setIsHint(EventCode.IS_WARNING);
						copyOnWriteArrayList.remove(electricId);//移除该电箱id
					}

			if(dataTreeVO.getTypeId() > typeId-1 && dataTreeVO.getTypeId() != GROUP_TYPE)
				break;
			*//*if(dataTreeVO.getStateId() != CLOSE)*//*
				dataTreeVO.setChildren(getDataTreeList(dataTreeVO.getId() ,roleId
						,typeId ,copyOnWriteArrayList));
		}

		return dataTreeVOS;
	}

	public List<DataTreeVO> get(Integer parentId ,Integer roleId ,Integer typeId){
		List<Integer> electricIds = deviceEventMapper.selectDeviceIdByType(EventCode.UNSOLVED);//获取未处理报警的设备对应的电箱id

		//CopyOnWriteArrayList,线程安全的集合类
		CopyOnWriteArrayList<Integer> COWElectricIds = new CopyOnWriteArrayList<>(electricIds);

		return getDataTreeList(parentId ,roleId ,typeId ,COWElectricIds);
	}

	*//**
	 * 显示“机构”和“分组”的信息
	 * @return “机构”和“分组”的信息
	 *//*
	public List<DataTree> getOrgOrGroupList(){
		return dataTreeMapper.selectOrgOrGroup();
	}

	*//**
	 * 返回所有的“分组”对象
	 * @return 所有的“分组”对象
	 *//*
	public List<DataTree> selectGroup(){
		return dataTreeMapper.selectGroup(GROUP_TYPE);
	}

	*//**
	 * 修改某结点的父母结点（拖动该结点或该名称）
	 * @param parentId 父母结点id
	 * @param id 结点id
	 * @return 修改数
	 *//*
	public int update(String name ,Integer parentId ,Integer id){
		Integer update;
		DataTree dataTree = new DataTree();
		if (name != null)
			dataTree.setName(name);
		dataTree.setParentId(parentId);
		dataTree.setId(id);
		update = dataTreeMapper.updateByPrimaryKeySelective(dataTree);
		//异步更新缓存
		asyncTask.updateDataTreeCache(DEVICE_TYPE);
		return update;
	}

	*//**
	 * 修改某结点的姓名或位置，传入的姓名或父母节点信息不一定是新的
	 * @param name 修改后的姓名
	 * @param newParentTypeInfo 新的父母节点信息
	 *//*
	public void updateData(String typeInfo ,String name ,String newParentTypeInfo ,int typeId){

		DataTree dataTree = new DataTree();
		int newParentId = dataTreeMapper.selectIdByTypeInfo(newParentTypeInfo);//获取新节点的id
		dataTree.setParentId(newParentId);
		dataTree.setName(name);
		dataTree.setTypeInfo(typeInfo);

		dataTreeMapper.updateByPrimaryKeySelective(dataTree);

		//异步更新缓存
		asyncTask.updateDataTreeCache(typeId);

	}

	*//**
	 * 删除分组的操作
	 * @return 删除数
	 *//*
	*//*@Transactional
	public int deleteGroup(DataTree group){
		Integer delete;
		Integer update = dataTreeMapper.updateDataParent(group.getId() ,group.getParentId());//修改分组下属的父母id
		//如果所属父母结点下属没有孩子，则将该结点的展开状态置为关
		if(update == 0)
			dataTreeMapper.update(new DataTree(group.getParentId() ,CLOSE));

		delete = dataTreeMapper.deleteByPrimaryKey(group.getId());//删除结点

		//更新缓存
		ELECTRIC_BOX_DATATREEVOS = getDataTreeList(-1 ,0 ,ELECTRIC_BOX_TYPE);
		DEVICE_DATATREEVOS = getDataTreeList(-1 ,0 ,DEVICE_TYPE);

		return delete;
	}*//*


*//*	@Transactional
	public void deleteData(DataTree dataTree){
		Integer childCount = dataTreeMapper.hasChild(dataTree.getParentId());
		//如果被删除结点的父母结点只有这个被删除的孩子，则将该父母结点的展开状态置为关
		if(childCount == 1)
			dataTreeMapper.update(new DataTree(dataTree.getParentId() ,CLOSE));

		//删除结点信息
		dataTreeMapper.deleteByStateInfo(dataTree.getTypeInfo());

		//更新缓存
		ELECTRIC_BOX_DATATREEVOS = getDataTreeList(-1 ,0 ,ELECTRIC_BOX_TYPE);
		DEVICE_DATATREEVOS = getDataTreeList(-1 ,0 ,DEVICE_TYPE);
		ORG_DATATREEVOS = getDataTreeList(-1 ,0 ,ORG_TYPE);

	}*/

	@Override
	public BaseDao<DataTree> getDao() {
		return dataTreeMapper;
	}
}
