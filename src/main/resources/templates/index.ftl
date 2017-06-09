<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
  	
	<title>EUROMOBY: forex rates</title>
	<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/css/index.css" />
</head>
<body>
	<div class="container">
		<div class="row">
			<h1 class="text-center">EURO forex rates</h1>
			<h4 class="text-center">${today?string("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'")}</h4>
			<table class="table table-striped">
			<thead>
			<tr>
				<th colspan="2">Currency</th>
				<th class="text-right">Spot</th>
			</tr>
			</thead>
			<tbody id="rates">
			</tbody>
			</table>
		</div>
	</div>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="/js/index.js"></script>	
</body>

</html>