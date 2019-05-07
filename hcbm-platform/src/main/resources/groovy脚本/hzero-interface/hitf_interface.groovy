package script.db

databaseChangeLog(logicalFilePath: 'script/db/hitf_interface.groovy') {
    changeSet(author: "shuangfei.zhu@hand-china.com", id: "2019-02-28-hitf_interface") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hitf_interface_s', startValue:"1")
        }
        createTable(tableName: "hitf_interface", remarks: "接口配置") {
            column(name: "interface_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "interface_server_id", type: "bigint(20)",  remarks: "服务配置ID")  {constraints(nullable:"false")}  
            column(name: "interface_code", type: "varchar(" + 30 * weight + ")",  remarks: "接口代码")  {constraints(nullable:"false")}  
            column(name: "interface_name", type: "varchar(" + 250 * weight + ")",  remarks: "接口名称")  {constraints(nullable:"false")}  
            column(name: "interface_url", type: "varchar(" + 255 * weight + ")",  remarks: "接口地址")   
            column(name: "publish_type", type: "varchar(" + 30 * weight + ")",   defaultValue:"REST",   remarks: "发布类型，代码：HITF.SERVICE_TYPE")  {constraints(nullable:"false")}  
            column(name: "mapping_class", type: "varchar(" + 255 * weight + ")",  remarks: "映射类，处理请求参数及响应格式的映射")   
            column(name: "request_method", type: "varchar(" + 30 * weight + ")",  remarks: "请求方式，代码：HITF.REQUEST_METHOD")   
            column(name: "request_header", type: "varchar(1800)",  remarks: "请求头")
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用，1代表启用，0代表禁用")  {constraints(nullable:"false")}  
            column(name: "soap_version", type: "varchar(" + 50 * weight + ")",  remarks: "SOAP版本，代码：HITF.SOAP_VERSION")   
            column(name: "soap_action", type: "varchar(" + 255 * weight + ")",  remarks: "SOAPACTION")   
            column(name: "invoke_record_details", type: "tinyint(1)",   defaultValue:"0",   remarks: "是否记录调用详情")  {constraints(nullable:"false")}  
            column(name: "status", type: "varchar(" + 30 * weight + ")",   defaultValue:"ENABLED",   remarks: "状态，代码：HITF.INTERFACE_STATUS, ENABLED/DISABLED/DISABLE_INPROGRESS")  {constraints(nullable:"false")}  
            column(name: "remark", type: "longtext",  remarks: "备注说明")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }
   createIndex(tableName: "hitf_interface", indexName: "hitf_interface_n1") {
            column(name: "interface_server_id")
            column(name: "enabled_flag")
        }

        addUniqueConstraint(columnNames:"interface_server_id,interface_code",tableName:"hitf_interface",constraintName: "hitf_interface_u1")
    }
}