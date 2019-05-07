/**
 * 子账户管理 租户级
 * todo 角色，部分： 1。 别的公司管理员分配的角色怎么办
 * @date 2018-0-07
 * @author WY
 * @email  yang.wang06@hand-china.com
 * @copyright Copyright (c) 2018, Hand
 */

import React from 'react';
import { connect } from 'dva';
import { Button, Modal, Tree } from 'hzero-ui';
import { isEmpty, map, join, isUndefined } from 'lodash';
import { Bind } from 'lodash-decorators';
import uuid from 'uuid/v4';
import queryString from 'query-string';

import { Header, Content } from 'components/Page';
import cacheComponent from 'components/CacheComponent';
import ExcelExport from 'components/ExcelExport';

import notification from 'utils/notification';
import formatterCollections from 'utils/intl/formatterCollections';
import intl from 'utils/intl';
import { getCurrentOrganizationId, getCurrentUser, filterNullValueObject } from 'utils/utils';
import { HZERO_IAM } from 'utils/config';
import { openTab } from 'utils/menuTab';

import Search from './Search';
import List from './List';
import EditForm from './EditForm';
import Password from './Password';
import UserGroupModal from './UserGroupModal';

const { TreeNode } = Tree;

@connect(({ subAccountOrg, loading }) => ({
  subAccountOrg,
  loading: loading.effects,
  updatingOne: loading.effects['subAccountOrg/updateOne'],
  creatingOne: loading.effects['subAccountOrg/createOne'],
  loadingList: loading.effects['subAccountOrg/fetchList'],
  loadingDetail: loading.effects['subAccountOrg/fetchDetail'],
  updatingPassword: loading.effects['subAccountOrg/updatePassword'],
  loadingCurrentUser: loading.effects['subAccountOrg/roleCurrent'], // 查询当前用户的角色
  loadingDistributeUsers: loading.effects['subAccountOrg/roleQueryAll'], // 查询可分配的所有角色
  organizationId: getCurrentOrganizationId(),
  currentUser: getCurrentUser(),
}))
@formatterCollections({ code: ['hiam.subAccount'] })
@cacheComponent({ cacheKey: '/hiam/sub-account-org/users' })
export default class SubAccountOrg extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      editModalProps: {},
      editFormProps: {},
      groupModalProps: {},
    };
  }

  /**
   * 组件加载完成后,初始化数据
   */
  componentDidMount() {
    const {
      dispatch,
      subAccountOrg: { pagination },
    } = this.props;
    dispatch({
      type: 'subAccountOrg/fetchEnum',
    });
    this.handleSearch(pagination);
  }

  /**
   * 根据查询条件调用查询接口
   * @param {Object} [pagination={}] 分页信息
   */
  @Bind()
  handleSearch(pagination = {}) {
    const { dispatch } = this.props;
    const filterValues = isUndefined(this.filterForm)
      ? {}
      : filterNullValueObject(this.filterForm.getFieldsValue());
    dispatch({
      type: 'subAccountOrg/fetchList',
      payload: {
        page: isEmpty(pagination) ? {} : pagination,
        ...filterValues,
      },
    });
  }

  @Bind()
  fetchAllRoles(fields) {
    const { dispatch } = this.props;
    return dispatch({
      type: 'subAccountOrg/roleQueryAll',
      payload: fields,
    });
  }

  /**
   * 查询对应用户 所拥有的角色
   * 给EditForm 使用
   * @param {!Number} payload.userId 用户id
   */
  @Bind()
  fetchUserRoles(payload) {
    const { dispatch } = this.props;
    return dispatch({
      type: 'subAccountOrg/roleCurrent',
      payload,
    });
  }

  /**
   * 获取用户组织树
   * @param {Number} userId - 用户id
   * @memberof SubAccountOrg
   */
  @Bind()
  fetchUserOrgTree(userId) {
    const { dispatch } = this.props;
    dispatch({
      type: 'subAccountOrg/queryUnitsTree',
      payload: { params: { userId } },
    });
  }

  /**
   * 新建按钮点击
   * 打开编辑弹框,并将isCreate(新建标志)置为true
   */
  @Bind()
  handleCreateBtnClick() {
    const { currentUser } = this.props;
    this.setState({
      editModalProps: {
        visible: true,
      },
      editFormProps: {
        isCreate: true,
        key: uuid(),
        tenantName: currentUser && currentUser.tenantName,
      },
    });
  }

  /**
   * 编辑
   * 打开编辑弹框,并将isCreate(新建标志)置为false
   * 并将 initialValue 设置成当前的帐号
   * @param {Object} record 当前操作的记录
   */
  @Bind()
  handleRecordEditBtnClick(record) {
    const { currentUser = {}, dispatch } = this.props;
    dispatch({
      type: 'subAccountOrg/fetchDetail',
      payload: {
        userId: record.id,
      },
    }).then(res => {
      if (res) {
        this.setState({
          editModalProps: {
            visible: true,
          },
          editFormProps: {
            initialValue: res,
            key: uuid(),
            isSameUser: currentUser.id === res.id,
            tenantName: res.tenantName,
            isCreate: false,
          },
        });
      }
    });
  }

  /**
   * 分配用户组
   * 打开用户组弹窗
   * @param {object} userRecord
   */
  @Bind()
  showGroupModal(userRecord) {
    const { currentUser = {} } = this.props;
    this.setState({
      groupModalProps: {
        isSameUser: currentUser.id === userRecord.id,
        visible: true,
        isCreate: false,
        userRecord,
      },
    });
  }

  /**
   * 关闭用户组模态框
   */
  @Bind()
  hiddenGroupModal() {
    this.setState({
      groupModalProps: {
        visible: false,
      },
    });
  }

  /**
   * 保存 用户组信息
   * @param saveData
   */
  @Bind()
  handleGroupModalOK(saveData) {
    const {
      dispatch,
      subAccountOrg: { pagination },
    } = this.props;
    dispatch({
      type: 'subAccountOrg/addUserGroup',
      payload: saveData,
    }).then(res => {
      if (res) {
        notification.success();
        this.hiddenGroupModal();
        this.handleSearch(pagination);
      }
    });
  }

  // TODO 接口
  /**
   * 删除用户组
   * @param {object[]} memberGroupList
   */
  @Bind()
  handleGroupRemove(params) {
    const { dispatch } = this.props;
    return dispatch({
      type: 'subAccountOrg/deleteUserGroup',
      payload: params,
    });
  }

  // TODO 接口
  /**
   * 查询 可分配的用户组
   * @param {object} params
   */
  @Bind()
  fetchGroups(params) {
    const { dispatch } = this.props;
    return dispatch({
      type: 'subAccountOrg/fetchGroups',
      payload: params,
    });
  }

  // TODO接口联调
  /**
   * 获取已经分配的用户组
   * @param {object} payload
   * @param {number} payload.userId 当前编辑帐号id
   * @param {pagination} payload.pagination 分页信息
   */
  @Bind()
  fetchCurrentUserGroups(payload) {
    const { dispatch } = this.props;
    return dispatch({
      type: 'subAccountOrg/getCurrentUserGroups',
      payload,
    });
  }

  /**
   * 授权管理
   * // todo
   * @param {Object} record 当前操作的记录
   */
  @Bind()
  handleRecordAuthManageBtnClick(record) {
    const { history } = this.props;
    history.push({
      pathname: '/hiam/sub-account-org/authority-management',
      search: `userId=${record.id}`,
    });
  }

  // /**
  //  * // FIXME: 这个按钮的事件没有用到, 到时候需要看下 到底需不需要
  //  * 授权维护
  //  * @param {Object} record 当前操作的记录
  //  */
  // @Bind()
  // handleRecordGrantBtnClick(record) {
  //   // eslint-disable-next-line
  //   console.log(record);
  // }

  /**
   * handleRecordUpdatePassword-修改密码按钮点击
   * @param {Object} record 帐号
   */
  @Bind()
  handleRecordUpdatePassword(record) {
    const { dispatch, currentUser } = this.props;
    dispatch({
      type: 'subAccountOrg/openPassword',
      payload: {
        userInfo: record,
        isSameUser: currentUser.id === record.id,
      },
    });
  }

  /**
   * handleClosePassword-关闭密码修改模态框
   */
  @Bind()
  handleClosePassword() {
    const { dispatch } = this.props;
    dispatch({
      type: 'subAccountOrg/closePassword',
    });
  }

  /**
   * handlePasswordUpdate-修改密码
   * @param {!Object} params 密码信息
   * @param {!String} params.password 新密码
   * @param {!String} params.password 确认新密码
   */
  @Bind()
  handlePasswordUpdate(params) {
    const {
      dispatch,
      subAccountOrg: { passwordProps: { userInfo = {}, isSameUser } = {} },
    } = this.props;
    const { id, organizationId: userOrganizationId } = userInfo;
    dispatch({
      type: 'subAccountOrg/updatePassword',
      payload: { id, userOrganizationId, isSameUser, ...params },
    }).then(res => {
      if (res) {
        notification.success();
        this.handleClosePassword();
      }
    });
  }

  /**
   * 编辑框确认按钮点击
   * 调用获取数据的接口, 根据isCreate 调用不同的 effect
   * 接口调用成功后,调用查询数据接口,刷新数据的objectVersionNumber
   */
  @Bind()
  handleEditModalOkBtnClick() {
    const {
      subAccountOrg: { pagination },
      dispatch,
    } = this.props;
    const {
      editFormProps: { isCreate },
    } = this.state;
    const data = this.editForm.getEditFormData();
    if (!isEmpty(data)) {
      let updateOrCreatePromise;
      if (isCreate) {
        // 创建新的帐号
        updateOrCreatePromise = dispatch({
          type: 'subAccountOrg/createOne',
          payload: { userInfo: data },
        });
      } else {
        // 更新之前的帐号
        updateOrCreatePromise = dispatch({
          type: 'subAccountOrg/updateOne',
          payload: { userInfo: data },
        });
      }
      updateOrCreatePromise.then(res => {
        if (res) {
          this.setState({
            editModalProps: {
              visible: false,
            },
          });
          notification.success();
          // 更新或保存完毕, 需要刷新 objectVersionNumber
          this.handleSearch(pagination);
        }
      });
    }
  }

  /**
   * 编辑框取消按钮点击
   * 将模态框影藏
   */
  @Bind()
  handleEditModalCancelBtnClick() {
    this.setState({
      editModalProps: {
        visible: false,
      },
    });
  }

  // 导出
  /**
   * 获取导出字段查询参数
   */
  @Bind()
  getExportQueryParams() {
    const { checkedKeys } = this.state;
    const fieldsValue = this.filterForm ? this.filterForm.getFieldsValue() : {};
    return {
      ...fieldsValue,
      authorityTypeQueryParams: join(checkedKeys, ','),
    };
  }

  /**
   * @function handleExpand - 节点展开
   * @param {array} expandedKeys - 展开的节点组成的数组
   */
  @Bind()
  handleExpand(expandedKeys) {
    this.setState({
      expandedKeys,
    });
  }

  /**
   * @function handleSelect - 选择项变化监控
   * @param {array}} checkedKeys - 选中项的 key 数组
   */
  @Bind()
  handleSelect(checkedKeys) {
    this.setState({ checkedKeys });
  }

  // 删除子账户下的角色
  @Bind()
  deleteRoles(memberRoleList) {
    const { dispatch } = this.props;
    return dispatch({
      type: 'subAccountOrg/deleteRoles',
      payload: {
        memberRoleList,
      },
    });
  }

  /**
   * 渲染权限维度的树
   */
  @Bind()
  renderExportTree() {
    const {
      subAccountOrg: { enumMap: { authorityType = [] } = {} },
    } = this.props;
    const { expandedKeys, checkedKeys } = this.state;
    if (isEmpty(authorityType)) {
      return null;
    } else {
      return (
        <Tree
          checkable
          onExpand={this.handleExpand}
          expandedKeys={expandedKeys}
          defaultExpandedKeys={['authorityType']}
          onCheck={this.handleSelect}
          checkedKeys={checkedKeys}
        >
          <TreeNode
            title={intl.get('hiam.subAccount.model.user.authorityType').d('权限维度')}
            key="authorityType"
          >
            {map(authorityType, item => {
              return <TreeNode title={item.meaning} key={item.value} />;
            })}
          </TreeNode>
        </Tree>
      );
    }
  }

  handleBatchImport() {
    openTab({
      key: `/hiam/sub-account-org/data-import/HIAM.ACCOUNT_CREATE`,
      search: queryString.stringify({
        title: 'hzero.common.title.batchImport',
        action: intl.get('hiam.subAccount.view.button.batchImport').d('账户导入'),
      }),
    });
  }

  handleRoleImport() {
    openTab({
      key: `/hiam/sub-account-org/data-import/HIAM.ROLE_CREATE`,
      search: queryString.stringify({
        title: 'hzero.common.title.roleImport',
        action: intl.get('hiam.subAccount.view.button.roleImport').d('角色导入'),
      }),
    });
  }

  handlePermissionImport() {
    openTab({
      key: `/hiam/sub-account-org/data-import/HIAM.AUTH_CREATE`,
      search: queryString.stringify({
        title: 'hzero.common.title.permissionImport',
        action: intl.get('hiam.subAccount.view.button.permissionImport').d('权限导入'),
      }),
    });
  }

  handleAuthorityCodeImport() {
    // openTab({
    //   key: `/hiam/sub-account-org/data-import/AUTH_CODE_CREATE`,
    //   search: queryString.stringify({
    //     title: 'hzero.common.title.authorityCodeImport',
    //     action: intl.get('hiam.subAccount.view.button.authorityCodeImport').d('授权编码导入'),
    //   }),
    // });
  }

  render() {
    const {
      subAccountOrg: {
        createSubRoles = [],
        enumMap = {},
        passwordProps = {},
        unitsTree = [],
        pagination = false,
        dataSource = [],
      },
      updatingPassword,
      loadingList,
      loadingDetail,
      updatingOne,
      creatingOne,
      loadingDistributeUsers,
      loadingCurrentUser,
      organizationId,
    } = this.props;
    const { editFormProps, editModalProps, groupModalProps = {} } = this.state;
    const { visible } = editModalProps;
    const filterProps = {
      onFilterChange: this.handleSearch,
      onRef: node => {
        this.filterForm = node.props.form;
      },
    };
    const listProps = {
      pagination,
      dataSource,
      loading: loadingList || loadingDetail,
      handleRecordEditBtnClick: this.handleRecordEditBtnClick,
      showGroupModal: this.showGroupModal,
      handleRecordAuthManageBtnClick: this.handleRecordAuthManageBtnClick,
      // handleRecordGrantBtnClick: this.handleRecordGrantBtnClick,
      handleRecordUpdatePassword: this.handleRecordUpdatePassword,
      searchPaging: this.handleSearch,
    };
    const modalProps = {
      ...editModalProps,
      closable: false,
      width: 1000,
      confirmLoading: creatingOne || updatingOne,
      wrapClassName: 'ant-modal-sidebar-right',
      transitionName: 'move-right',
      onOk: this.handleEditModalOkBtnClick,
      onCancel: this.handleEditModalCancelBtnClick,
      title: editFormProps.isCreate
        ? intl.get('hiam.subAccount.view.message.title.userCreate').d('帐号新建')
        : intl.get('hiam.subAccount.view.message.title.userEdit').d('帐号编辑'),
    };
    const editProps = {
      ...editFormProps,
      idd: enumMap.idd || [],
      gender: enumMap.gender || [],
      organizationId,
      createSubRoles,
      unitsTree,
      loadingDistributeUsers,
      loadingCurrentUser,
      deleteRoles: this.deleteRoles,
      level: enumMap.level || [],
      fetchUserRoles: this.fetchUserRoles,
      fetchAllRoles: this.fetchAllRoles,
      fetchUserOrgTree: this.fetchUserOrgTree,
      defaultRoleId: editFormProps.initialValue && editFormProps.initialValue.defaultRoleId,
      onRef: node => {
        this.editForm = node;
      },
    };
    return (
      <React.Fragment>
        <Header title={intl.get('hiam.subAccount.view.message.title').d('子账户管理')}>
          <Button type="primary" icon="plus" onClick={this.handleCreateBtnClick}>
            {intl.get('hzero.common.button.create').d('新建')}
          </Button>
          <Button icon="to-top" onClick={this.handleBatchImport.bind(this)}>
            {intl.get('hiam.subAccount.view.button.batchImport').d('账户导入')}
          </Button>
          <Button icon="to-top" onClick={this.handleRoleImport.bind(this)}>
            {intl.get('hiam.subAccount.view.button.roleImport').d('角色导入')}
          </Button>
          <Button icon="to-top" onClick={this.handlePermissionImport.bind(this)}>
            {intl.get('hiam.subAccount.view.button.permissionImport').d('权限导入')}
          </Button>
          <Button icon="to-top" onClick={this.handleAuthorityCodeImport.bind(this)}>
            {intl.get('hiam.subAccount.view.button.authorityCodeImport').d('授权码导入')}
          </Button>
          <ExcelExport
            requestUrl={`${HZERO_IAM}/hzero/v1/${organizationId}/users/export`}
            queryParams={this.getExportQueryParams()}
            queryFormItem={this.renderExportTree()}
            otherButtonProps={{ className: 'label-btn' }}
          />
        </Header>
        <Content>
          <Search {...filterProps} />
          <List {...listProps} />
        </Content>
        {!!visible && (
          <Modal {...modalProps}>
            <EditForm {...editProps} />
          </Modal>
        )}
        <Password
          {...passwordProps}
          confirmLoading={updatingPassword}
          onOk={this.handlePasswordUpdate}
          onCancel={this.handleClosePassword}
        />
        {groupModalProps.visible && (
          <UserGroupModal
            key="group-modal"
            {...groupModalProps}
            onCancel={this.hiddenGroupModal}
            onOk={this.handleGroupModalOK}
            fetchGroups={this.fetchGroups}
            fetchCurrentUserGroups={this.fetchCurrentUserGroups}
            onGroupRemove={this.handleGroupRemove}
          />
        )}
      </React.Fragment>
    );
  }
}
