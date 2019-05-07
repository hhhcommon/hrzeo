package script.db

databaseChangeLog(logicalFilePath: 'script/db/hptl_portal_assign.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2018-10-25-hptl_portal_assign") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hptl_portal_assign_s', startValue:"1")
        }
        createTable(tableName: "hptl_portal_assign", remarks: "门户分配") {
        column(name: "assign_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
        column(name: "group_id", type: "bigint(20)",  remarks: "集团ID HPFM_GROUP.GROUP_ID")  {constraints(nullable:"false")}  
        column(name: "tenant_id", type: "bigint(20)",  remarks: "客户租户ID")  {constraints(nullable:"false")}  
        column(name: "company_id", type: "bigint(20)",   defaultValue:"0",   remarks: "客户公司ID，HPFM_COMPANY.COMPANY_ID")   
        column(name: "web_url", type: "varchar(255)",  remarks: "域名")  {constraints(nullable:"false")}  
        column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "启用标识")  {constraints(nullable:"false")}  
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

    }

   addUniqueConstraint(columnNames:"web_url",tableName:"hptl_portal_assign",constraintName: "hptl_portal_assign_u1")   
   addUniqueConstraint(columnNames:"company_id,group_id",tableName:"hptl_portal_assign",constraintName: "hptl_portal_assign_u2")   
  }    
}