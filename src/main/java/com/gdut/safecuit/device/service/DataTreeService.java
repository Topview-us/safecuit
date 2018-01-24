package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.common.DataTreeTypeCode;
import com.gdut.safecuit.device.common.po.*;
import com.gdut.safecuit.device.common.vo.DataTreeVO;
import com.gdut.safecuit.device.dao.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.gdut.safecuit.common.DataTreeTypeCode.*;
import static com.gdut.safecuit.device.common.util.DataCache.*;

/**
 * Created by Garson in 21:07 2018/1/20
 * Description :
 */
@Service
public class DataTreeService extends BaseServiceImpl<DataTree> {

	@Resource
	private DataTreeMapper dataTreeMapper;
	/*@Resource
	private AreaMapper areaMapper;
	@Resource
	private CityMapper cityMapper;
	@Resource
	private ProvinceMapper provinceMapper;*/
	/*@Resource
	private DeviceMapper deviceMapper;
	@Resource
	private ElectricBoxMapper electricBoxMapper;*/

	/**
	 * 添加“分组”的service
	 * @param dataTree “分组”的数据对象,属性包括分组名、分组的父母结点id
	 * @return 添加数
	 */
	@Transactional
	public int insertGroup(DataTree dataTree){

		dataTree.setTypeId(GROUP_TYPE);//设置为分组类型
		dataTree.setStateId(CLOSE);//设置为关状态
		//dataTree.setRoleId(0);

		//判断是否在同级中有相同名字的分组
		if(dataTreeMapper.selectByNameAndParentId(dataTree.getName() ,dataTree.getParentId()) == 0)
			return -1;
		//判断该结点的上一级原先有没有孩子
		if(dataTreeMapper.hasChild(dataTree.getParentId()) == 0)
			dataTreeMapper.updateParentState(dataTree.getParentId());

		//更新缓存
		ELECTRIC_BOX_DATATREEVOS = getDataTreeList(-1 ,0 ,ORG_TYPE);
		DEVICE_DATATREEVOS = getDataTreeList(-1 ,0 ,ELECTRIC_BOX_TYPE);

		return dataTreeMapper.insert(dataTree);
	}

	/**
	 * 添加“机构”结点,用于添加机构时添加其在数据树上对应数据的方法
	 * @param orgId 添加的机构的id
	 * @return 添加数
	 */
	@Transactional
	public int insertOrg(Integer orgId){

		Integer insert;
	/*	Area area = areaMapper.selectByPrimaryKey(orgId);
		City city = cityMapper.selectByPrimaryKey(area.getCityId());
		Province province = provinceMapper.selectByPrimaryKey(city.getProvinceId());

		int lastDataId = dataTreeMapper.selectFinalDataId();//找出表中最后一条数据的id

		//先查看是否存在数据，再决定是否将数据插入树中
		if(dataTreeMapper.selectIdByName(province.getName()) != null)
			dataTreeMapper.insert(new DataTree(lastDataId+1 ,province.getName() ,1, "province" ,1,-1 ,0));
		if(dataTreeMapper.selectIdByName(city.getName()) != null)
			dataTreeMapper.insert(new DataTree(lastDataId+2 ,city.getName() ,2 ,"city" ,1 ,lastDataId+1 ,0));
		if(dataTreeMapper.selectIdByName(area.getName()) != null)
			dataTreeMapper.insert(new DataTree(lastDataId+3 ,area.getName() ,3 ,"area" ,1 ,lastDataId+2 ,0));
*/
		DataTree dataTree = new DataTree();

		//selectOrgInfo:查找添加的机构的信息
		//dataTree.setName();
		//dataTree.setTypeInfo();

		//dataTree.setParentId();

		dataTree.setTypeId(ORG_TYPE); //设置为机构类型
		dataTree.setStateId(CLOSE); //设置为关状态
		//dataTree.setRoleId(0);


		//判断该结点的上一级原先有没有孩子
		if(dataTreeMapper.hasChild(dataTree.getParentId()) == 0)
			dataTreeMapper.updateParentState(dataTree.getParentId());

		//机构数据插入树中
		insert = dataTreeMapper.insert(dataTree);

		//更新缓存
		updateCache(ORG_TYPE);

		return insert;
	}

	/**
	 * 添加“电箱”或“设备”结点
	 * @param id 设备或电箱的id
	 * @param typeId 设备或电箱的结点类型id
	 */
	public void insertElectricBoxOrDevice(String id ,String name ,String parentTypeInfo ,int typeId){

		DataTree dataTree;
		Integer parentId;

		if(typeId == DEVICE_TYPE){
		//	Device device = deviceMapper.selectByPrimaryKey(id);
			parentId = dataTreeMapper.selectIdByTypeInfo(parentTypeInfo);
			dataTree = new DataTree(name ,DEVICE_TYPE ,id, CLOSE ,parentId);
		}else {
		//	ElectricBox electricBox = electricBoxMapper.selectByPrimaryKey(id);
			parentId = dataTreeMapper.selectIdByTypeInfo(parentTypeInfo);
			dataTree = new DataTree(name ,ELECTRIC_BOX_TYPE ,id ,CLOSE ,parentId);
		}

		//判断该结点的上一级原先有没有孩子
		if(dataTreeMapper.hasChild(dataTree.getParentId()) == 0)
			dataTreeMapper.updateParentState(parentId);

		dataTreeMapper.insert(dataTree);

		//更新缓存
		updateCache(typeId);

	}

	/**
	 * 删除“机构”、“电箱”、“设备”的结点
	 * @param id 设备或电箱或机构的id
	 * @param typeId 设备或电箱或机构的结点类型id
	 */
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

		//更新缓存
		updateCache(typeId);

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
	/**
	 * 获取数据树数据，以树形结构输出
	 * @param parentId 父母结点id
	 * @return 数据树集合
	 */
	public List<DataTreeVO> getDataTreeList(Integer parentId ,Integer roleId ,Integer typeId){
		List<DataTreeVO> dataTreeVOS = getDataTree(parentId ,roleId);
		for (DataTreeVO dataTreeVO:dataTreeVOS) {
			if(dataTreeVO.getTypeId() > typeId-1 && dataTreeVO.getTypeId() != GROUP_TYPE)
				break;
			if(dataTreeVO.getStateId() != CLOSE)
				dataTreeVO.setChildren(getDataTreeList(dataTreeVO.getId(),roleId,typeId));
		}

		return dataTreeVOS;
	}

	/**
	 * 显示“机构”和“分组”的信息
	 * @return “机构”和“分组”的信息
	 */
	public List<DataTree> getOrgOrGroupList(){
		return dataTreeMapper.selectOrgOrGroup();
	}

	/**
	 * 返回所有的“分组”对象
	 * @return 所有的“分组”对象
	 */
	public List<DataTree> selectGroup(){
		return dataTreeMapper.selectGroup(GROUP_TYPE);
	}

	/**
	 * 修改某结点的父母结点（拖动该结点或该名称）
	 * @param parentId 父母结点id
	 * @param id 结点id
	 * @return 修改数
	 */
	public int update(String name ,Integer parentId ,Integer id){
		DataTree dataTree = new DataTree();
		if (name != null)
			dataTree.setName(name);
		dataTree.setParentId(parentId);
		dataTree.setId(id);
		return dataTreeMapper.updateByPrimaryKeySelective(dataTree);
	}

	/**
	 * 修改某结点的姓名或位置，传入的姓名或父母节点信息不一定是新的
	 * @param name 修改后的姓名
	 * @param newParentTypeInfo 新的父母节点信息
	 */
	public void updateData(String typeInfo ,String name ,String newParentTypeInfo ,int typeId){

		DataTree dataTree = new DataTree();
		int newParentId = dataTreeMapper.selectIdByTypeInfo(newParentTypeInfo);//获取新节点的id
		dataTree.setParentId(newParentId);
		dataTree.setName(name);
		dataTree.setTypeInfo(typeInfo);

		dataTreeMapper.updateByPrimaryKeySelective(dataTree);

		//更新缓存
		updateCache(typeId);

	}

	/**
	 * 删除分组的操作
	 * @return 删除数
	 */
	/*@Transactional
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
	}*/


/*	@Transactional
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

	private void updateCache(int typeId){
		//更新缓存
		if(typeId == DEVICE_TYPE)
			DEVICE_DATATREEVOS = getDataTreeList(-1 ,0 ,ELECTRIC_BOX_TYPE);
		else if(typeId == ELECTRIC_BOX_TYPE){
			System.out.println("cache");
			ELECTRIC_BOX_DATATREEVOS = getDataTreeList(-1 ,0 ,ORG_TYPE);
			DEVICE_DATATREEVOS = getDataTreeList(-1 ,0 ,ELECTRIC_BOX_TYPE);
		}else {
			ORG_DATATREEVOS = getDataTreeList(-1 ,0 ,AREA_TYPE);
			ELECTRIC_BOX_DATATREEVOS = getDataTreeList(-1 ,0 ,ORG_TYPE);
			DEVICE_DATATREEVOS = getDataTreeList(-1 ,0 ,ELECTRIC_BOX_TYPE);
		}
	}

	@Override
	public BaseDao<DataTree> getDao() {
		return dataTreeMapper;
	}
}
