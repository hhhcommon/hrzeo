package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_stencil_file.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_stencil_file") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_stencil_file_s', startValue:"1")
        }
        createTable(tableName: "hwfl_stencil_file", remarks: "模版文件") {
        column(name: "stencil_file_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "stencil_file_name", type: "varchar(50)",  remarks: "文件名称")  {constraints(nullable:"false")}  
        column(name: "stencil_file", type: "longtext",  remarks: "文件")  {constraints(nullable:"false")}  
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

   addUniqueConstraint(columnNames:"stencil_file_name",tableName:"hwfl_stencil_file",constraintName: "hwfl_stencil_file_u1")
  }    
}