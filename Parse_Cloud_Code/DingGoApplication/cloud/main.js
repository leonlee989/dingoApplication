Parse.Cloud.define("averageStar", function(request, response) {	
	getCompanyObject(request.params.company, function(company) {
		
		if (company != undefined) {
			var query = new Parse.Query("review");
			query.equalTo("companyId", company);
			query.find({
				success: function(results) {
					
					if (results.length) {
						var foodRating = 0;
						var valueRating = 0;
						var ambienceRating = 0;
						var serviceRating = 0;

						for (var index in results) {
							var review = results[index];
							
							foodRating += review.get("food_drink");
							valueRating += review.get("value");
							ambienceRating += review.get("ambience");
							serviceRating += review.get("service");
						}

						var averageFoodRating = foodRating / results.length;
						var averageValueRating = valueRating / results.length;
						var averageAmbienceRating = ambienceRating / results.length;
						var averageServiceRating = serviceRating / results.length;

						var averageRating = {
							food_drink: averageFoodRating.toFixed(1),
							value: averageValueRating.toFixed(1),
							ambience: averageAmbienceRating.toFixed(1),
							service: averageServiceRating.toFixed(1),
							total: ((averageFoodRating + averageValueRating + averageAmbienceRating + averageServiceRating) / 4).toFixed(1)
						};

						response.success(averageRating);
					} else {
						response.error("No results found");
					}
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

Parse.Cloud.define("Logger", function(request, response) {
	console.log(request.params);
	response.success();
});