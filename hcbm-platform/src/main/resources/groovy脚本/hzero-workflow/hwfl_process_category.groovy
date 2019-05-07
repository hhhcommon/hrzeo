package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_process_category.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_process_category") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_process_category_s', startValue:"1")
        }
        createTable(tableName: "hwfl_process_category", remarks: "流程分类表") {
        column(name: "process_category_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "code", type: "varchar(50)",  remarks: "流程分类代码")  {constraints(nullable:"false")}  
        column(name: "description", type: "varchar(255)",  remarks: "流程分类描述")  {constraints(nullable:"false")}  
        column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "组织id")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

   addUniqueConstraint(columnNames:"tenant_id,code",tableName:"hwfl_process_category",constraintName: "hwfl_process_category_u1")
  }    
}