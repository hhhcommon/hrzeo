package script.db

databaseChangeLog(logicalFilePath: 'script/db/hmsg_receiver_type.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2019-01-02-hmsg_receiver_type") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hmsg_receiver_type_s', startValue:"1")
        }
        createTable(tableName: "hmsg_receiver_type", remarks: "接收者类型") {
            column(name: "receiver_type_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "type_code", type: "varchar(" + 30 * weight + ")",  remarks: "类型代码")  {constraints(nullable:"false")}  
            column(name: "type_name", type: "varchar(" + 120 * weight + ")",  remarks: "类型名称")  {constraints(nullable:"false")}  
            column(name: "route_name", type: "varchar(" + 120 * weight + ")",  remarks: "目标路由")  {constraints(nullable:"false")}  
            column(name: "api_url", type: "varchar(" + 480 * weight + ")",  remarks: "服务URL")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",  remarks: "启用标识")   
            column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "租户ID,hpfm_tenant.tenant_id")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"type_code,tenant_id",tableName:"hmsg_receiver_type",constraintName: "hmsg_receiver_type_u1")
    }
}