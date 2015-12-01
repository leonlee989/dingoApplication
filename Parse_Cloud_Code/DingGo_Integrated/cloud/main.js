Parse.Cloud.define("averageStar", function(request, response) {	
	getCompanyObject(request.params.company, function(company) {
		
		if (company != undefined) {
			var query = new Parse.Query("review");
			query.equalTo("companyId", company);
			query.find({
				success: function(results) {
					
					var foodRating = 0.0;
					var valueRating = 0.0;
					var ambienceRating = 0.0;
					var serviceRating = 0.0;

					for (var index in results) {
						var review = results[index];
						
						foodRating += review.get("food_drink");
						valueRating += review.get("value");
						ambienceRating += review.get("ambience");
						serviceRating += review.get("service");
					}

					if (results.length) {
						var foodRating = foodRating / results.length;
						var valueRating = valueRating / results.length;
						var ambienceRating = ambienceRating / results.length;
						var serviceRating = serviceRating / results.length;
					}

					var averageRating = {
						numReviews: results.length,
						food_drink: parseFloat(foodRating.toFixed(1)),
						value: parseFloat(valueRating.toFixed(1)),
						ambience: parseFloat(ambienceRating.toFixed(1)),
						service: parseFloat(serviceRating.toFixed(1)),
						total: parseFloat(((foodRating + valueRating + ambienceRating + serviceRating) / 4).toFixed(1))
					};

					response.success(averageRating);
				},
				error: function() {
					response.error("Unable to retrieve reviews from Parse");
				}
			});
		} else {
			response.error("Unable to retrieve any company with the following Id: " + request.params.company);
		}
	});
});

var getCompanyObject = function(companyId, callback) {
		var query = new Parse.Query("company");
		query.get(companyId, {
				success: function(company) {
					callback(company);
				},
				error: function(object, error) {
					callback();
				}
		});
};

Parse.Cloud.define("PushDealToAll", function(request, response) {
	var query = new Parse.Query(Parse.Installation);
	
	var dealName = request.params.deal;
	var companyName = request.params.company;
	
	Parse.Push.send({
		where: query,
		data: {
			alert: "Ding! " + companyName + ": " + dealName + "!"
		}
	}, {
		success: function() {
			response.success("Notification has been pushed")
		}, 
		error : function(error) {
			response.error(error.getMessage);
		}
	});
});

Parse.Cloud.define("Logger", function(request, response) {
	console.log(request.params);
	response.success();
});