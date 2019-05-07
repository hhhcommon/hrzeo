package script.db

databaseChangeLog(logicalFilePath: 'script/db/hitf_interface_server.groovy') {
    changeSet(author: "shuangfei.zhu@hand-china.com", id: "2019-02-28-hitf_interface_server") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hitf_interface_server_s', startValue:"1")
        }
        createTable(tableName: "hitf_interface_server", remarks: "服务配置") {
            column(name: "interface_server_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "server_code", type: "varchar(" + 30 * weight + ")",  remarks: "服务代码")  {constraints(nullable:"false")}  
            column(name: "server_name", type: "varchar(" + 250 * weight + ")",  remarks: "服务名称")  {constraints(nullable:"false")}  
            column(name: "service_type", type: "varchar(" + 30 * weight + ")",  remarks: "服务类型，代码：HITF.SERVICE_TYPE")  {constraints(nullable:"false")}  
            column(name: "domain_url", type: "varchar(" + 200 * weight + ")",  remarks: "服务地址")  {constraints(nullable:"false")}  
            column(name: "auth_type", type: "varchar(" + 30 * weight + ")",   defaultValue:"NONE",   remarks: "认证模式，代码：HITF.AUTH_TYPE")  {constraints(nullable:"false")}  
            column(name: "grant_type", type: "varchar(" + 30 * weight + ")",  remarks: "授权模式，代码：HITF.GRANT_TYPE")   
            column(name: "access_token_url", type: "varchar(" + 255 * weight + ")",  remarks: "获取Token的URL")   
            column(name: "client_id", type: "varchar(" + 255 * weight + ")",  remarks: "客户端ID")   
            column(name: "client_secret", type: "varchar(" + 255 * weight + ")",  remarks: "客户端密钥")   
            column(name: "auth_username", type: "varchar(" + 80 * weight + ")",  remarks: "认证用户名")   
            column(name: "auth_password", type: "varchar(" + 255 * weight + ")",  remarks: "认证密码")   
            column(name: "scope", type: "varchar(" + 255 * weight + ")",  remarks: "权限范围")   
            column(name: "soap_namespace", type: "varchar(" + 80 * weight + ")",  remarks: "SOAP命名空间")   
            column(name: "soap_element_prefix", type: "varchar(" + 30 * weight + ")",  remarks: "SOAP参数前缀标识")   
            column(name: "soap_wss_password_type", type: "varchar(" + 30 * weight + ")",  remarks: "SOAP加密类型")   
            column(name: "soap_username", type: "varchar(" + 80 * weight + ")",  remarks: "校验用户名")   
            column(name: "soap_password", type: "varchar(" + 255 * weight + ")",  remarks: "校验密码")   
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用。1启用，0未启用")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"server_code,tenant_id",tableName:"hitf_interface_server",constraintName: "hitf_interface_server_u1")
    }
}