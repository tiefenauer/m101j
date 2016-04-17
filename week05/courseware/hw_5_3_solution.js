var result = db.grades.aggregate([
	{
		"$unwind": "$scores"
	}	
	,{
		"$match": {
			"scores.type": {
				"$ne": "quiz"
			}
		}
	}	
	,{
		"$group":{
			"_id": {
				"student_id": "$student_id",
				"class_id": "$class_id"				
			},
			"avg_student_score": {
				"$avg": "$scores.score"
			}
		}
	}			
	,{
		"$group": {
			"_id": "$_id.class_id",
			"avg_class_score": {
				"$avg": "$avg_student_score"
			}
		}
	}	
	,{
		"$sort" :{
			"avg_class_score" : -1
		}
	}
]);

printjson(result._batch);