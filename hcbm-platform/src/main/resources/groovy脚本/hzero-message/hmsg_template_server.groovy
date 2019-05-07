package script.db

databaseChangeLog(logicalFilePath: 'script/db/hmsg_template_server.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2019-01-02-hmsg_template_server") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hmsg_template_server_s', startValue:"1")
        }
        createTable(tableName: "hmsg_template_server", remarks: "消息模板账户") {
            column(name: "temp_server_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
            column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "租户ID,hpfm_tenant.tenant_id")  {constraints(nullable:"false")}  
            column(name: "message_code", type: "varchar(" + 30 * weight + ")",  remarks: "消息代码")  {constraints(nullable:"false")}  
            column(name: "message_name", type: "varchar(" + 120 * weight + ")",  remarks: "消息名称")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用标识")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"tenant_id,message_code",tableName:"hmsg_template_server",constraintName: "hmsg_template_server_u1")
    }
}