const API_BASE_URL = "https://203.161.56.189:8443/api/v1";
const BEARER_TOKEN = '';
const FORMATTER = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'EUR',
});
$(document).ready(function(){
  const urlParams = new URLSearchParams(window.location.search);
  if (urlParams.has('accountnumber')) {
    const accountNumber = urlParams.get('accountnumber');
    $("#accountnumber").val(accountNumber).trigger("change");
    $("#btn_fetch_transactions").trigger("click");
  }
  
	$("#btn_submit_current_account").click(function() {
		const customerId = $("#customerid").val();
		const initialCredit = $("#initialicredit").val();
		const data = {
			customerId,
			initialCredit
		}
		if (customerId === "") return alert("Customer Id should not be empty");

		$.ajax({
	        type: 'POST',
	        dataType: 'json',
          contentType: 'application/json',
	        url: API_BASE_URL+'/open-current-account',
	        data: JSON.stringify(data),
	        headers: {
	        	'Authorization': 'Bearer '+BEARER_TOKEN
	        },
	        success: function(response){
	        	console.log(response);
	        	alert(response?.message);
	            if (response.code === 200) {
	            	$("#customerid").val("").trigger("change");
	            	$("#initialicredit").val("").trigger("change");
	            }
	        },
	        error: function(err) {
	            console.log(err);
              if (err.responseJSON) {
                const { code, status, message } = err.responseJSON;
                alert(message);
              }
	        }
	    });
	})
  
  $("#btn_fetch_transactions").click(function() {
		const accountnumber = $("#accountnumber").val();
		if (accountnumber === "") return alert("Account number should not be empty");

		$.ajax({
	        type: 'GET',
	        dataType: 'json',
          contentType: 'application/json',
	        url: API_BASE_URL+'/transactions/'+accountnumber,
	        headers: {
	        	'Authorization': 'Bearer '+BEARER_TOKEN
	        },
	        success: function(response){
            const data = response?.data || [];
            let html = '';
            let i = 1;
            if (data?.length < 1) return alert("No transactions found!");
            $("#transactions_table").html("");
            for (const d of data) {
              
              const type = accountnumber === d?.source_account_number ? 'DEBIT' : 'CREDIT';
              const acc = type === "CREDIT" ? d?.source_account_number : d?.destination_account_number;
              html += '<tr><td>'+i+'</td><td>'+FORMATTER.format(Number(d?.amount))+'</td><td>'+type+'</td><td>'+acc+'</td><td>'+d?.createdAt+'</td></tr>';
              i += 1;
            }
            $("#transactions_table").html(html);
	        },
	        error: function(err) {
	            console.log(err);
              if (err.responseJSON) {
                const { code, status, message } = err.responseJSON;
                alert(message);
              }
	        }
	    });
	})
  
  $("#btn_fetch_customer").click(function() {
		const customerid = $("#customerid").val();
		if (customerid === "") return alert("Customer ID should not be empty");

		$.ajax({
	        type: 'GET',
	        dataType: 'json',
          contentType: 'application/json',
	        url: API_BASE_URL+'/customer/'+customerid,
	        headers: {
	        	'Authorization': 'Bearer '+BEARER_TOKEN
	        },
	        success: function(response){
	        	console.log(response);
            $("#customername").html(response?.data?.fullName)
            $("#customeremail").html(response?.data?.email)
            $("#customer_id").html(response?.data?.customerId)
            $(".hide-show").removeClass("d-none");
            const accounts = response?.data?.accounts || [];
            let html = '';
            let i = 1;
            $("#accounts_table").html("");
            for (const account of accounts) {              
              html += '<tr><td>'+i+'</td><td>'+account?.accountNumber+'</td><td>'+account?.accountType+'</td><td>'+FORMATTER.format(Number(account?.accountBalance))+'</td><td>'+account?.createdAt+'</td><td><a href="transactions.html?accountnumber='+account?.accountNumber+'">Transactions</a></td></tr>';
              i += 1;
            }
            $("#accounts_table").html(html);
	        },
	        error: function(err) {
	            console.log(err);
              if (err.responseJSON) {
                const { code, status, message } = err.responseJSON;
                alert(message);
              }
	        }
	    });
	})
})