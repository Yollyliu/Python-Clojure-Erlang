(ns a2.core
  (:require [clojure.set :as set])
   (:gen-class))


 
(def cust_map { })
(def prod_map { })
(def p_map { })
(def sOrig_map { })
(def nameMixed { })
(def name_map { })
(def itemMixed { })
(def item_map { })
(def sales_cust { })
(def sales_prod { })
(def sales_count { })


 (defn cust_data []
;   (def cust_map { })
   (def cust (slurp "cust.txt"))
   (def cust (clojure.string/split-lines cust ))
   (doseq [each-entry cust]
     [
      (def cust_record (clojure.string/split each-entry #"\|"))
      (def custInt_ID (get cust_record 0))
      (def cust_ID(read-string custInt_ID))
      (def nameM (get cust_record 1))
      (def namelower (clojure.string/lower-case nameM))
      (def content(rest cust_record ))
      (def nameMixed (assoc nameMixed cust_ID nameM))
      (def name_map (assoc name_map cust_ID namelower))
      (def cust_map (assoc cust_map cust_ID content))
      ]
     )
   (def cust_map (into  (sorted-map)  cust_map))
   (let [result name_map] result)
   (let [result nameMixed] result)
   (let [result cust_map] result))
 
;   (println cust_map))
; 
  (defn prod_data []
;   (def prod_map { })
   (def prod (slurp "prod.txt"))
   (def prod (clojure.string/split-lines prod ))
   (doseq [each-entry prod]
     [
      (def prod_record (clojure.string/split each-entry #"\|"))
      (def itemStr_ID (get prod_record 0))
      (def item_ID (read-string itemStr_ID))
      (def itemM(get prod_record 1))
      (def item_name(clojure.string/lower-case itemM))
      (def content(rest prod_record ))
      (def itemMixed (assoc itemMixed item_ID itemM))
      (def item_map (assoc item_map item_ID item_name))
      (def prod_map (assoc prod_map item_ID content))
      ]
     )
   (def prod_map (into  (sorted-map)  prod_map))
   (let [result itemMixed] result)
   (let [result item_map] result)
   (let [result prod_map] result))
  
;   (println prod_map))
 
 (defn printCust []
    (doseq [each-entry cust_map]
     [
      (println each-entry)
      ]))
 
 (defn printProd []
    (doseq [each-entry prod_map]
     [ 
      (println each-entry)]))
   
 
(defn printSales []
   (doseq [each-entry p_map]
     [ 
      (println each-entry)]))
   
  
(defn select-values [map ks]
         (reduce #(conj %1 (map %2)) [] ks))

;  <salesID, custID, prodID, itemCount>

  (defn sales_data []
   (def p_map { })
   (def p (slurp "sales.txt"))
   (def p (clojure.string/split-lines p ))
   (doseq [each-entry p]
     [
      (def sale_record (clojure.string/split each-entry #"\|"))
      (def saleStr_ID (get sale_record 0))
      (def sale_ID (read-string saleStr_ID))
      (def origContent (rest sale_record))
      (def sOrig_map (assoc sOrig_map sale_ID origContent))
      
      
      (def nameStr_ID (get sale_record 1))
      (def name_ID (read-string nameStr_ID))

;      (def strName (str nameID))
      (def name_infor(select-values cust_map [name_ID]))
;      (println name_infor)
      (def cust(get name_infor 0))
      (def custName(nth cust 0))
      (def sales_cust (assoc sales_cust sale_ID custName))


      (def prodStr_ID (get sale_record 2))
      (def prodID (read-string prodStr_ID))
;      (def strProd(str prodID))
      (def prod_infor(select-values prod_map [prodID]))
      (def prod(get prod_infor 0))
      (def prodName(nth prod 0))
      (def sales_prod (assoc sales_prod sale_ID prodName))

      (def number (get sale_record 3))
      (def sales_count (assoc sales_prod sale_ID number))
;      
      (def new_content [custName prodName number])
      (def p_map (assoc p_map sale_ID new_content))
      ]
     )
   (def p_map (into  (sorted-map)  p_map))
   (let [result sOrig_map] result)
   (let [result sales_cust] result)
   (let [result sales_prod] result)
   (let [result sales_count] result)
   (let [result p_map] result))
 
 (defn calculate_sum [nameS]
;    "1" ((def nameInt (filter (comp #{name} name_map) (keys name_map)))
;   (println name_map)
;   (def name (str nameInt))
;   (println "name" name)
   (def nameInt (filter (comp #{nameS} name_map) (keys name_map)))
;   (println nameInt)
   (def nameID(nth nameInt 0))
;   (def nameID (read-string nameStr))
;   (println "nameID" nameID)
   (def sales_information(vals sOrig_map))
   (doseq [each-entry sales_information]
;     (println "record" each-entry)
     [
      
      (def curCustStr(nth each-entry 0))
      (def curCust(read-string curCustStr))
      (def prodIdStr(nth each-entry 1))
      (def prodId(read-string prodIdStr))
      (def strCount(nth each-entry 2))
      (def curCount(read-string strCount))
;      (println "prodId" prodId)
;      (println "curCount" curCount)
;      (println "curCust" curCust)
;      (println "nameID" nameID)
      (if (= nameID curCust)
        ( do 
        (def prod_rec(select-values prod_map [prodId]))
;        (println "prod_rec" prod_rec)
        (def prod_infor(get prod_rec 0))
        (def strPrice(nth prod_infor 1))
        (def prod_price(read-string strPrice))
;        (def prod_price (format "%.2f" prodInt_price))
;        (println "prod_price" prod_price)
        (def cur (* prod_price curCount))
;        (println "current money:" cur)
        (def sum (+ cur sum))
        ) true) 
        ])
   (def sum (format "%.2f" sum) )
   (let [result sum] result))
;   (print name)
;   (print ": $")
;   (println sum))
    
  
  

 (defn total_sales []
   (def nameCollection (vals nameMixed))
   (doseq [each-entry nameCollection]
     [
      (print each-entry)
      (print "      ")
      ]
     )
   (def sum 0.0)
   (println "Please enter a customer's name:")
   (def nameM (read-line))
   (def nameL (clojure.string/lower-case nameM))
   (def flag "0")
   (doseq [each-entry nameCollection]
     [  
      (def curMixed(str each-entry))
      (def cur_name(clojure.string/lower-case curMixed))
      (if (= cur_name nameL) 
        (do 
          (def flag "1")) true)
      ])
    (if (= flag "0")
      (do
        (println "Wrong Input! The customer is not recorded in database."))
;        (total_sales))
      (println (str nameM ": $" (calculate_sum nameL)))))
   

 
(defn calculate_count [item]

  (def itemInfor (filter (comp #{item} item_map) (keys item_map)))
;  (println "itemInfor" itemInfor)
   (def itemId (nth itemInfor 0))
;   (print "itemId" itemId)
;   (def itemId (read-string itemIdStr))
;   (println "itemId" itemId)
   (def sale_record(vals sOrig_map))
    (doseq [each-entry sale_record]
;     (println "record" each-entry)
     [
      (def prodIdStr (nth each-entry 1))
      (def prodId (read-string prodIdStr))
      
      (def strCount(nth each-entry 2))
      (def curCount(read-string strCount))
;      (println "prodId" prodId)
;      (println "curCount" curCount)
;      (println "itemId" itemId)

      (if (= itemId prodId)
        ( do 
        (def count_item (+ curCount count_item))) true)
;        (println "count_item:" count_item))true)
        ])
;    (def count_item (format "%.2f" count_item) )
    (let [result count_item] result))
;    (print item)
;    (print ": ")
;    (println count_item))
  
  
 (defn total_count []
   (def itemCollection (vals itemMixed))
   (print "Please enter an product:")
   (println itemCollection)
   (def count_item 0)
   (def item_M (read-line))
   (def item( clojure.string/lower-case item_M))
   (def flag "0")
    (doseq [each-entry itemCollection]
     [  
      (def cur_item(str each-entry))
      (if (= cur_item item) 
        (do 
          (def flag "1")) true)
      ])
    
;    (println "flag" flag)
;    (case flag
;      "0" ((println "The product is not in database.") (total_count))
;      "1" (calculate_count item)
;      "default" (total_count)))
     ( if (= flag "0")
       (do
         (println "Wrong Input! The customer is not recorded in database."))
;         (total_count))
       (println (str item_M ":" (calculate_count item)))))






;   
;   (def itemInt (filter (comp #{item} item_map) (keys item_map)))
;   (def itemId (nth itemInt 0))
;   (println "itemId" itemId)
;   (def sale_record(vals sOrig_map))
;    (doseq [each-entry sale_record]
;     (println "record" each-entry)
;     [
;      (def prodId(nth each-entry 1))
;      (def strCount(nth each-entry 2))
;      (def curCount(read-string strCount))
;      (println "prodId" prodId)
;      (println "curCount" curCount)
;      (println "itemId" itemId)
;      (if (= itemId prodId)
;        ( do 
;        (def count_item (+ curCount count_item))) true)
;        (println "count_item:" count_item))true)
;        ])
;    (print item)
;    (print ": ")
;    (println count_item))))
 
 (defn menu []
  (println)
  (println)
  (println "*** Sales Menu ***")
  (println "------------------")
  (println )
  (println "1. Display Customer Table")
  (println "2. Display Product Table")
  (println "3. Display Sales Table")
  (println "4. Total Sales for Customer")
  (println "5. Total Count for Product")
  (println "6. Exit")
  (println )
  (println "Enter an option?")
  (def option (read-line))
    (case option
      "1" ((printCust) (menu))
      "2" ((printProd) (menu))
      "3" ((printSales) (menu))
      "4" ((total_sales) (menu))
      "5" ((total_count) (menu))
      "6" ((println "ByeBye") (System/exit 0))
      "defult")
    (println "Wrong input!")
    (menu))
;  
(cust_data)
(prod_data)
(sales_data)
(menu)


;(defn select-values [map ks]
;         (reduce #(conj %1 (map %2)) [] ks))
;(defn get_values [] 
;  (cust_data)
;  (println cust_map)
;  (def name(1))
;  (def sn(str name))
;  (def infor(select-values cust_map [sn]))
;  (println infor))
;
;(get_values)
   
                              