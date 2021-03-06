/**
 * 服务编码字段 需要存储2个值 serviceId, serviceCode
 */

import React from 'react';
import { Form, Input, Modal, Spin } from 'hzero-ui';
import { Bind } from 'lodash-decorators';

import Switch from 'components/Switch';
import Lov from 'components/Lov';

import intl from 'utils/intl';
import { isTenantRoleLevel } from 'utils/utils';

const FormItem = Form.Item;

const formLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 17 },
};

@Form.create({ fieldNameProp: null })
export default class ServiceRouteForm extends React.PureComponent {
  @Bind()
  handleOK() {
    const { form, onOk } = this.props;
    form.validateFields((err, fieldsValue) => {
      if (!err) {
        onOk(fieldsValue);
      }
    });
  }

  render() {
    const {
      form,
      title,
      modalVisible,
      loading,
      onCancel,
      initData = {},
      initLoading = false,
    } = this.props;
    const { getFieldDecorator, setFieldsValue } = form;
    const {
      serviceId,
      serviceCode,
      serviceName,
      name,
      path,
      url,
      sensitiveHeaders,
      helperService,
      customSensitiveHeaders = 0,
      stripPrefix = 1,
      retryable = 0,
    } = initData;
    return (
      <Modal
        destroyOnClose
        wrapClassName="ant-modal-sidebar-right"
        transitionName="move-right"
        title={title}
        width={620}
        visible={modalVisible}
        confirmLoading={loading}
        onCancel={onCancel}
        onOk={this.handleOK}
      >
        <Spin spinning={initLoading}>
          <Form>
            <FormItem
              {...formLayout}
              label={intl.get('hsgp.common.model.common.serviceCode').d('服务编码')}
            >
              {getFieldDecorator('serviceId', {
                initialValue: serviceId,
                rules: [
                  {
                    required: true,
                    message: intl.get('hzero.common.validation.notNull', {
                      name: intl.get('hsgp.common.model.common.serviceCode').d('服务编码'),
                    }),
                  },
                ],
              })(
                <Lov
                  code={isTenantRoleLevel() ? 'HCNF.SERVICE.ORG' : 'HCNF.SERVICE'}
                  textValue={serviceCode}
                  textField="serviceCode"
                  disabled={!!serviceId}
                  onChange={(val, item) => {
                    setFieldsValue({
                      serviceName: item.serviceName,
                    });
                  }}
                />
              )}
            </FormItem>
            <FormItem
              {...formLayout}
              label={intl.get('hsgp.common.model.common.serviceName').d('服务名称')}
            >
              {getFieldDecorator('serviceName', {
                initialValue: serviceName,
                rules: [
                  {
                    required: true,
                    message: intl.get('hzero.common.validation.notNull', {
                      name: intl.get('hsgp.common.model.common.serviceName').d('服务名称'),
                    }),
                  },
                ],
              })(<Input disabled />)}
            </FormItem>
            <FormItem
              {...formLayout}
              label={intl.get('hsgp.serviceRoute.model.serviceRoute.name').d('路由标识')}
            >
              {getFieldDecorator('name', {
                initialValue: name,
                rules: [
                  {
                    required: true,
                    message: intl.get('hzero.common.validation.notNull', {
                      name: intl.get('hsgp.serviceRoute.model.serviceRoute.name').d('路由标识'),
                    }),
                  },
                  {
                    max: 60,
                    message: intl.get('hzero.common.validation.max', {
                      max: 60,
                    }),
                  },
                ],
              })(<Input disabled={!!name} inputChinese={false} />)}
            </FormItem>
            <FormItem
              {...formLayout}
              label={intl.get('hsgp.serviceRoute.model.serviceRoute.path').d('服务路径')}
            >
              {getFieldDecorator('path', {
                initialValue: path,
                rules: [
                  {
                    required: true,
                    message: intl.get('hzero.common.validation.notNull', {
                      name: intl.get('hsgp.serviceRoute.model.serviceRoute.path').d('服务路径'),
                    }),
                  },
                  {
                    max: 120,
                    message: intl.get('hzero.common.validation.max', {
                      max: 120,
                    }),
                  },
                ],
              })(<Input disabled={!!path} inputChinese={false} />)}
            </FormItem>
            <FormItem
              {...formLayout}
              label={intl.get('hsgp.serviceRoute.model.serviceRoute.url').d('物理路径')}
            >
              {getFieldDecorator('url', {
                initialValue: url,
                rules: [
                  {
                    max: 240,
                    message: intl.get('hzero.common.validation.max', {
                      max: 240,
                    }),
                  },
                ],
              })(<Input inputChinese={false} />)}
            </FormItem>
            <FormItem
              {...formLayout}
              label={intl.get('hsgp.serviceRoute.model.serviceRoute.stripPrefix').d('去掉前缀')}
            >
              {getFieldDecorator('stripPrefix', {
                initialValue: stripPrefix,
              })(<Switch />)}
            </FormItem>
            <FormItem
              {...formLayout}
              label={intl.get('hsgp.serviceRoute.model.serviceRoute.retryable').d('支持路由重试')}
            >
              {getFieldDecorator('retryable', {
                initialValue: retryable,
              })(<Switch />)}
            </FormItem>
            <FormItem
              {...formLayout}
              label={intl
                .get('hsgp.serviceRoute.model.serviceRoute.customSensitiveHeaders')
                .d('自定义敏感头')}
            >
              {getFieldDecorator('customSensitiveHeaders', {
                initialValue: customSensitiveHeaders,
              })(<Switch />)}
            </FormItem>
            <FormItem
              {...formLayout}
              label={intl
                .get('hsgp.serviceRoute.model.serviceRoute.helperService')
                .d('敏感头部列表')}
            >
              {getFieldDecorator('sensitiveHeaders', {
                initialValue: sensitiveHeaders,
                rules: [
                  {
                    max: 240,
                    message: intl.get('hzero.common.validation.max', {
                      max: 240,
                    }),
                  },
                ],
              })(<Input inputChinese={false} />)}
            </FormItem>
            <FormItem {...formLayout} label="自定义GatewayHelper">
              {getFieldDecorator('helperService', {
                initialValue: helperService,
                rules: [
                  {
                    max: 30,
                    message: intl.get('hzero.common.validation.max', {
                      max: 30,
                    }),
                  },
                ],
              })(<Input inputChinese={false} />)}
            </FormItem>
          </Form>
        </Spin>
      </Modal>
    );
  }
}
