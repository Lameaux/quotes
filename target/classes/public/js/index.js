$(function() {

	var currencies = [];
	
	$.get("/api/currencies", function(data) {
		data.map(function(currency){
			currencies[currency.code] = currency.name;
		});
		
		loadRates(currencies);
	});	

});

function loadRates(currencies) {
	$.get("/api/rates/spot", function(data) {
		data.map(function(rate){
			
			var row = '<tr>';
			row += '<td><img src="/img/flags/' + rate.currency + '.gif"> ' + rate.currency + '</td>';
			row += '<td>' + currencies[rate.currency] + '</td>';
			row += '<td class="text-right">' + rate.value + '</td>';
			row += '</tr>';
			
			$('#rates').append(row);
		})
	});	
}
