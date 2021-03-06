import React from 'react';
import { Form, Input, Modal, Select, Spin, Divider } from 'hzero-ui';
import { Bind } from 'lodash-decorators';

import intl from 'utils/intl';

const FormItem = Form.Item;
const { Option } = Select;

const formLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 17 },
};

@Form.create({ fieldNameProp: null })
export default class DeployPlatformForm extends React.PureComponent {
  /**
   * 保存部署平台
   */
  @Bind()
  handleOK() {
    const { form, onOk } = this.props;
    form.validateFieldsAndScroll((err, fieldsValue) => {
      if (!err) {
        onOk(fieldsValue);
      }
    });
  }

  render() {
    const {
      form,
      initData,
      title,
      modalVisible,
      loading,
      onCancel,
      initLoading = false,
      grantTypeList = [],
      linkPlatformList = [],
    } = this.props;
    const { getFieldDecorator } = form;
    const { platformCode, platformName, sourceKey, description, config = {} } = initData;
    const { clientId = '', clientSecret, username, password, organizationId, grantType } = config;
    return (
      <Modal
        destroyOnClose
        wrapClassName="ant-modal-sidebar-right"
        transitionName="move-right"
        title={title}
        visible={modalVisible}
        confirmLoading={loading}
        onCancel={onCancel}
        onOk={this.handleOK}
      >
        <Spin spinning={initLoading}>
          <Form>
            <Divider orientation="left">
              {intl.get('hsgp.common.view.message.editor.basicInfo').d('基本信息')}
            </Divider>
            <FormItem
              {...formLayout}
              label={intl
                .get('hsgp.deployPlatform.model.deployPlatform.platformCode')
                .d('平台编码')}
            >
              {getFieldDecorator('platformCode', {
                initialValue: platformCode,
                rules: [
                  {
                    required: true,
                    message: intl.get('hzero.common.validation.notNull', {
                      name: intl
                        .get('hsgp.deployPlatform.model.deployPlatform.platformCode')
                        .d('平台编码'),
                    }),
                  },
                  {
                    max: 60,
                    message: intl.get('hzero.common.validation.max', {
                      max: 60,
                    }),
                  },
                  {
                    pattern: /^[A-Z0-9][A-Z0-9-_.]*$/,
                    message: intl.get('hsgp.common.validation.code').d('编码格式不正确'),
                  },
                ],
              })(<Input disabled={!!platformCode} inputChinese={false} />)}
            </FormItem>
            <FormItem
              {...formLayout}
              label={intl
                .get('hsgp.deployPlatform.model.deployPlatform.platformName')
                .d('平台名称')}
            >
              {getFieldDecorator('platformName', {
                initialValue: platformName,
                rules: [
                  {
                    required: true,
                    message: intl.get('hzero.common.validation.notNull', {
                      name: intl
                        .get('hsgp.deployPlatform.model.deployPlatform.platformName')
                        .d('平台名称'),
                    }),
                  },
                  {
                    max: 60,
                    message: intl.get('hzero.common.validation.max', {
                      max: 60,
                    }),
                  },
                ],
              })(<Input />)}
            </FormItem>
            <FormItem
              {...formLayout}
              label={intl.get('hsgp.common.model.common.description').d('描述')}
            >
              {getFieldDecorator('description', {
                initialValue: description,
                rules: [
                  {
                    max: 240,
                    message: intl.get('hzero.common.validation.max', {
                      max: 240,
                    }),
                  },
                ],
              })(<Input />)}
            </FormItem>
            <FormItem
              {...formLayout}
              label={intl.get('hsgp.deployPlatform.model.deployPlatform.sourceName').d('连接平台')}
            >
              {getFieldDecorator('sourceKey', {
                initialValue: sourceKey,
                rules: [
                  {
                    required: true,
                    message: intl.get('hzero.common.validation.notNull', {
                      name: intl
                        .get('hsgp.deployPlatform.model.deployPlatform.sourceName')
                        .d('连接平台'),
                    }),
                  },
                ],
              })(
                <Select disabled={!!sourceKey}>
                  {linkPlatformList.map(item => {
                    return (
                      <Option key={item.value} value={item.value}>
                        {item.meaning}
                      </Option>
                    );
                  })}
                </Select>
              )}
            </FormItem>
            {form.getFieldValue('sourceKey') === 'choerodon' && (
              <React.Fragment>
                <Divider orientation="left">
                  {intl.get('hsgp.common.view.message.editor.authInfo').d('授权信息')}
                </Divider>
                <FormItem
                  {...formLayout}
                  label={intl.get('hsgp.common.model.common.grantType').d('授权类型')}
                >
                  {getFieldDecorator('grantType', {
                    initialValue: grantType,
                    rules: [
                      {
                        required: true,
                        message: intl.get('hzero.common.validation.notNull', {
                          name: intl.get('hsgp.common.model.common.grantType').d('授权类型'),
                        }),
                      },
                    ],
                  })(
                    <Select style={{ width: '100%' }}>
                      {grantTypeList.map(item => {
                        return (
                          <Option value={item.value} key={item.value}>
                            {item.meaning}
                          </Option>
                        );
                      })}
                    </Select>
                  )}
                </FormItem>
                <FormItem
                  {...formLayout}
                  label={intl.get('hsgp.common.model.common.clientId').d('客户端ID')}
                >
                  {getFieldDecorator('clientId', {
                    initialValue: clientId,
                    rules: [
                      {
                        required: true,
                        message: intl.get('hzero.common.validation.notNull', {
                          name: intl.get('hsgp.common.model.common.clientId').d('客户端ID'),
                        }),
                      },
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
                  label={intl.get('hsgp.common.model.common.clientSecret').d('客户端密钥')}
                >
                  {getFieldDecorator('clientSecret', {
                    initialValue: clientSecret,
                    rules: [
                      {
                        required: true,
                        message: intl.get('hzero.common.validation.notNull', {
                          name: intl.get('hsgp.common.model.common.clientSecret').d('客户端密钥'),
                        }),
                      },
                      {
                        max: 240,
                        message: intl.get('hzero.common.validation.max', {
                          max: 240,
                        }),
                      },
                    ],
                  })(<Input type="password" inputChinese={false} />)}
                </FormItem>
                <FormItem
                  {...formLayout}
                  label={intl.get('hsgp.common.model.common.username').d('用户名')}
                >
                  {getFieldDecorator('username', {
                    initialValue: username,
                    rules: [
                      {
                        required: true,
                        message: intl.get('hzero.common.validation.notNull', {
                          name: intl.get('hsgp.common.model.common.username').d('用户名'),
                        }),
                      },
                      {
                        max: 240,
                        message: intl.get('hzero.common.validation.max', {
                          max: 240,
                        }),
                      },
                    ],
                  })(<Input />)}
                </FormItem>
                <FormItem
                  {...formLayout}
                  label={intl.get('hsgp.common.model.common.password').d('密码')}
                >
                  {getFieldDecorator('password', {
                    initialValue: password,
                    rules: [
                      {
                        required: true,
                        message: intl.get('hzero.common.validation.notNull', {
                          name: intl.get('hsgp.common.model.common.password').d('密码'),
                        }),
                      },
                      {
                        max: 240,
                        message: intl.get('hzero.common.validation.max', {
                          max: 240,
                        }),
                      },
                    ],
                  })(<Input type="password" />)}
                </FormItem>
                <FormItem
                  {...formLayout}
                  label={intl.get('hsgp.common.model.common.organizationId').d('组织ID')}
                >
                  {getFieldDecorator('organizationId', {
                    initialValue: organizationId,
                    rules: [
                      {
                        required: true,
                        message: intl.get('hzero.common.validation.notNull', {
                          name: intl.get('hsgp.common.model.common.organizationId').d('组织ID'),
                        }),
                      },
                      {
                        max: 240,
                        message: intl.get('hzero.common.validation.max', {
                          max: 240,
                        }),
                      },
                    ],
                  })(<Input inputChinese={false} />)}
                </FormItem>
              </React.Fragment>
            )}
          </Form>
        </Spin>
      </Modal>
    );
  }
}
