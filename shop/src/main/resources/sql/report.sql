SELECT id,barcode,name,priceTable.price,priceTable.count ,(priceTable.count*priceTable.price) total
FROM good ,
  (SELECT sum(transgood.count) COUNT, price, innerTable.good_id
   FROM (transgood
         INNER JOIN good ON transgood.good_id=good.id) ,
     (SELECT good_id
      FROM transgood
      GROUP BY good_id) AS innerTable
   WHERE transgood.good_id = innerTable.good_id and transgood.id in 
   (SELECT OUTCOMEGOODS_ID
           FROM OUTCOMETRANS_TRANSGOOD
           WHERE OUTCOMETRANS_ID IN
               (SELECT ID
                FROM OUTCOMETRANS
                WHERE DATE BETWEEN ? AND ?))
   GROUP BY price) priceTable
WHERE priceTable.good_id = id