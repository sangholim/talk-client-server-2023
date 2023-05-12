#!/bin/bash
RS_STATUS=$(mongosh --quiet localhost:27017 --eval "rs.status().ok")

if [[ $RS_STATUS != 1 ]]
then
    # replica set configuration
    cfg="{
        _id: 'rs0',
        version: 1,
        members: [
            {
                "_id": 1,
                "host": 'mongo:27017',
                "priority": 1
            }
        ]
    }"
    mongosh localhost:27017 --eval "JSON.stringify(db.adminCommand({'replSetInitiate' : $cfg}))"
    echo "[INFO] Replication set initiated."
else
  echo "[INFO] Replication set already initiated."
fi
