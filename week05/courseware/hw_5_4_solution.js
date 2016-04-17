var result = db.zips.aggregate([
    {
    	"$project": {
    		"city": 1,
			"first_char": {
				"$substr" : ["$city",0,1]
			},
			"pop": 1
     	}     	
   }
   ,{
	   	"$match": {
	     		"first_char": {
	     			"$in": ["0","1","2","3","4","5","6","7","8","9"]
	     		}
	     	}
   }
   ,{
   	 "$project": {
   	 	"numeric" : {
   	 		"$literal": "numeric"
   	 	},
   	 	"pop": 1   	 	
   	 }
   }
   ,{
   		"$group": {
   			"_id": "numeric",
   			"sum": {
   				"$sum": "$pop"
   			}
   		}
   }
]);

printjson(result._batch);