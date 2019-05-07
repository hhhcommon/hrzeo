/**
 * tenants - 租户维护
 * @date: 2018-8-4
 * @author: YB <bo.yang02@hand-china.com>
 * @version: 0.0.1
 * @copyright Copyright (c) 2018, Hand
 */
import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Button, Table } from 'hzero-ui';
import { isUndefined, isEmpty } from 'lodash';
import { Bind } from 'lodash-decorators';

import { Header, Content } from 'components/Page';
import formatterCollections from 'utils/intl/formatterCollections';
import { enableRender } from 'utils/renderer';
import intl from 'utils/intl';
import notification from 'utils/notification';
import { filterNullValueObject } from 'utils/utils';

import FilterForm from './FilterForm';
import TenantForm from './TenantForm';

// const promptCode = 'hpfm.tenants';
/**
 * 租户信息维护
 * @extends {Component} - PureComponent
 * @reactProps {Object} [match={}] - react-router match路由信息
 * @reactProps {Object} [history={}]
 * @reactProps {Object} tenants - 数据源
 * @reactProps {boolean} loading - 数据加载是否完成
 * @reactProps {boolean} saving - 保存操作是否完成
 * @reactProps {Object} form - 表单对象
 * @reactProps {Function} [dispatch= e => e] - redux dispatch方法
 * @return React.element
 */
@connect(({ tenants, loading }) => ({
  tenants,
  loading: loading.effects['tenants/queryTenant'],
  saving: loading.effects['tenants/updateTenant'] || loading.effects['tenants/addTenant'],
}))
@formatterCollections({ code: ['entity.tenant'] })
export default class Tenants extends PureComponent {
  state = {
    modalVisible: false,
  };

  tenantForm;

  componentDidMount() {
    this.handleSearchTenant();
  }

  /**
   * 按条件查询
   */
  @Bind()
  handleSearchTenant(payload = {}) {
    const { dispatch } = this.props;
    const { form } = this.state;
    const filterValues = isUndefined(form) ? {} : filterNullValueObject(form.getFieldsValue());
    dispatch({
      type: 'tenants/queryTenant',
      payload: {
        page: isEmpty(payload) ? {} : payload,
        ...filterValues,
      },
    });
  }

  /**
   *
   * @param {object} ref - FilterForm子组件对象
   */
  @Bind()
  handleBindRef(ref = {}) {
    this.setState({ form: ref.props.form });
  }

  @Bind()
  showEditModal(tenantSrouce) {
    this.setState({ tenantSrouce }, () => {
      this.handleModalVisible(true);
    });
  }

  @Bind()
  showAddModal() {
    this.setState(
      {
        tenantSrouce: { enabledFlag: 1, ebankAccountFlag: 1 },
      },
      () => {
        this.handleModalVisible(true);
      }
    );
  }

  @Bind()
  hideModal() {
    const { saving = false } = this.props;
    if (!saving) {
      this.handleModalVisible(false);
    }
  }

  /**
   *是否打开模态框
   *
   * @param {*} flag true--打开 false--关闭
   * @memberof Tenants
   */
  handleModalVisible(flag) {
    if (flag === false && this.tenantForm) {
      this.tenantForm.resetForm();
    }
    this.setState({ modalVisible: !!flag });
  }

  /**
   *新增或者编辑
   *
   * @param {*} fieldsValue 表单数据
   * @memberof tenants
   */
  @Bind()
  handleAdd(fieldsValue) {
    const {
      dispatch,
      tenants: { pagination = {} },
    } = this.props;
    const { tenantSrouce = {} } = this.state;
    const { _token } = tenantSrouce;
    if (tenantSrouce.tenantId || tenantSrouce.tenantId === 0) {
      dispatch({
        type: 'tenants/updateTenant',
        payload: {
          _token,
          tenantId: tenantSrouce.tenantId,
          objectVersionNumber: tenantSrouce.objectVersionNumber,
          ...fieldsValue,
        },
      }).then(res => {
        if (res) {
          this.hideModal();
          this.handleSearchTenant(pagination);
          notification.success();
        }
      });
    } else {
      dispatch({
        type: 'tenants/addTenant',
        payload: {
          ...fieldsValue,
        },
      }).then(res => {
        if (res) {
          this.hideModal();
          this.handleSearchTenant(pagination);
          notification.success();
        }
      });
    }
  }

  render() {
    const {
      tenants: { tenantData = {}, pagination },
      loading,
      saving,
    } = this.props;
    const { content = [] } = tenantData;
    const { tenantSrouce = {}, modalVisible } = this.state;
    const { tenantId } = tenantSrouce;
    const filterProps = {
      onSearch: this.handleSearchTenant,
      onRef: this.handleBindRef,
    };
    const columns = [
      {
        title: intl.get('entity.tenant.code').d('租户编码'),
        width: 200,
        dataIndex: 'tenantNum',
      },
      {
        title: intl.get('entity.tenant.name').d('租户名称'),
        dataIndex: 'tenantName',
      },
      {
        title: intl.get('hzero.common.status').d('状态'),
        width: 100,
        align: 'center',
        dataIndex: 'enabledFlag',
        render: enableRender,
      },
      {
        title: intl.get('hzero.common.button.action').d('操作'),
        width: 100,
        align: 'center',
        dataIndex: 'option',
        render: (_, record) => {
          return (
            <a
              onClick={() => {
                this.showEditModal(record);
              }}
            >
              {intl.get('hzero.common.button.edit').d('编辑')}
            </a>
          );
        },
      },
    ];
    return (
      <React.Fragment>
        <Header title={intl.get('entity.tenant.maintain').d('租户维护')}>
          <Button icon="plus" type="primary" onClick={this.showAddModal}>
            {intl.get('hzero.common.button.create').d('新建')}
          </Button>
        </Header>
        <Content>
          <div className="table-list-search">
            <FilterForm {...filterProps} />
          </div>
          <Table
            bordered
            rowKey="tenantId"
            loading={loading}
            dataSource={content}
            columns={columns}
            pagination={pagination}
            onChange={this.handleSearchTenant}
          />
        </Content>
        <TenantForm
          sideBar
          title={
            tenantId !== undefined
              ? intl.get('entity.tenant.edit').d('租户编辑')
              : intl.get('entity.tenant.create').d('租户新建')
          }
          onRef={ref => {
            this.tenantForm = ref;
          }}
          data={tenantSrouce}
          handleAdd={this.handleAdd}
          confirmLoading={saving}
          modalVisible={modalVisible}
          hideModal={this.hideModal}
        />
      </React.Fragment>
    );
  }
}
