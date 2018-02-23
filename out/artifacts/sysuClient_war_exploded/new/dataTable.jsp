<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'dataTable.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.7/css/jquery.dataTables.css">
 
<!-- jQuery -->
<script type="text/javascript" charset="utf8" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
 
<!-- DataTables -->
<script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.7/js/jquery.dataTables.js"></script>
<body>
 <table id="table_id" class="display">
    <thead>
        <tr>
            <th>name</th>
            <th>position</th>
             <th>salary</th>
              <th>office</th>
             
        </tr>
    </thead>
    <tbody>
      
    </tbody>
</table>
<script>
  function Employee ( name, position, salary, office ) {
    this.name = name;
    this.position = position;
    this.salary = salary;
    this._office = office;
 
    this.office = function () {
        return this._office;
    };
};
 
	  $(document).ready( function () {
	 
	  $('#table_id').DataTable( {
	  
     data: [
        new Employee( "Tiger Nixon", "System Architect", "$3,120", "Edinburgh" ),
        new Employee( "Garrett Winters", "Director", "$5,300", "Edinburgh" )
    ],
    columns: [
        { data: 'name' },
        { data: 'salary' },
        { data: 'office()' },
        { data: 'position' }
    ]
} );
} );
</script>
  </body>
  
</html>
