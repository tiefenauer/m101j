var result = db.zips.aggregate([
	{
		"$match": { 
			"$or": [ 
						{"state": "CA"}, 
						{ "state": "NY"}
					]
			} 
	}, 
	{
		"$group": {
			"_id": {
				"state": "$state",
				"city": "$city"
			},
			"sum_pop": {
				"$sum": "$pop"
			}
		}
	}, 
	{
		"$match": {
			"sum_pop": {
				"$gt": 25000
			}
		}
	}, 
	{
		"$project": {
			"sum_pop": 1,
			"state": {
				"$literal": "NYCA"
			}
		}
	}, 
	{
		"$group": {
			"_id": "$state",
			"avg_pop": {
				"$avg": "$sum_pop"
			}
		}
	}
]).pretty();
printjson(result._batch);
