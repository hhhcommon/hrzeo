/**
 * 卡片管理 分配租户 查询表单
 * @date 2019-01-23
 * @author WY yang.wang06@hand-china.com
 * @copyright © HAND 2019
 */

import React from 'react';
import PropTypes from 'prop-types';
import { Form, Input, Button, DatePicker } from 'hzero-ui';
import { Bind } from 'lodash-decorators';

import intl from 'utils/intl';
import { getDateTimeFormat } from 'utils/utils';

const dateTimePickerStyle = {
  width: '160px',
};

/**
 * 卡片管理查询表单
 * @ReactProps {!Function} onRef - 拿到该组件的this
 * @ReactProps {!Function} onSearch - 触发查询方法
 */
@Form.create({ fieldNameProp: null })
export default class CardTenantEditSearchForm extends React.Component {
  static propTypes = {
    onSearch: PropTypes.func.isRequired, // 查询按钮点击触发
    onRef: PropTypes.func.isRequired, // 获取本省的this
  };

  componentDidMount() {
    const { onRef } = this.props;
    onRef(this);
  }

  /**
   * 注册时间从 的禁用 日期的方法
   * @param {moment} currentDate 选中的 时间
   */
  @Bind()
  beginDateDisabledDate(currentDate) {
    const { form } = this.props;
    const endDate = form.getFieldValue('endDate');
    return endDate && endDate.isBefore(currentDate, 'day');
  }

  /**
   * 注册时间至 的禁用 日期的方法
   * @param {moment} currentDate 选中的 时间
   */
  @Bind()
  endDateDisabledDate(currentDate) {
    const { form } = this.props;
    const beginDate = form.getFieldValue('beginDate');
    return beginDate && beginDate.isAfter(currentDate, 'day');
  }

  render() {
    const { form } = this.props;
    return (
      <Form layout="inline">
        <Form.Item label={intl.get('entity.tenant.name').d('租户名称')}>
          {form.getFieldDecorator('tenantName')(<Input />)}
        </Form.Item>
        <Form.Item label={intl.get('hzero.common.date.register.from').d('注册时间从')}>
          {form.getFieldDecorator('beginDate')(
            <DatePicker
              showTime
              style={dateTimePickerStyle}
              placeholder=""
              format={getDateTimeFormat()}
              disabledDate={this.beginDateDisabledDate}
            />
          )}
        </Form.Item>
        <Form.Item label={intl.get('hzero.common.date.register.to').d('注册时间至')}>
          {form.getFieldDecorator('endDate')(
            <DatePicker
              showTime
              style={dateTimePickerStyle}
              placeholder=""
              format={getDateTimeFormat()}
              disabledDate={this.endDateDisabledDate}
            />
          )}
        </Form.Item>
        <Form.Item>
          <Button key="search" type="primary" onClick={this.handleSearchBtnClick}>
            {intl.get('hzero.common.button.search').d('查询')}
          </Button>
          <Button key="reset" onClick={this.handleResetBtnClick}>
            {intl.get('hzero.common.button.reset').d('重置')}
          </Button>
        </Form.Item>
      </Form>
    );
  }

  @Bind()
  handleSearchBtnClick() {
    const { onSearch } = this.props;
    onSearch();
  }

  @Bind()
  handleResetBtnClick() {
    const { form } = this.props;
    form.resetFields();
  }
}
