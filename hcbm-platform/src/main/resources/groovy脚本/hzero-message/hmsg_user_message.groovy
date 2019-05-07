package script.db

databaseChangeLog(logicalFilePath: 'script/db/hmsg_user_message.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2019-01-02-hmsg_user_message") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hmsg_user_message_s', startValue:"1")
        }
        createTable(tableName: "hmsg_user_message", remarks: "用户消息") {
            column(name: "user_message_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "user_id", type: "bigint(20)",  remarks: "用户ID")  {constraints(nullable:"false")}  
            column(name: "message_id", type: "bigint(20)",  remarks: "消息ID，hmsg_message.message_id")  {constraints(nullable:"false")}  
            column(name: "read_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "已读标识")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "from_tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "来源租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"user_id,message_id,tenant_id",tableName:"hmsg_user_message",constraintName: "hmsg_user_message_u1")
    }
}