(ns clj-jena.core
  (:import [com.hp.hpl.jena.rdf.model Resource]
           [com.hp.hpl.jena.rdf.model SimpleSelector]
           [com.hp.hpl.jena.query QueryFactory
            QueryExecutionFactory]
           [com.hp.hpl.jena.tdb TDBFactory]))

(defn get-tdb-model
  [dir-name]
  (TDBFactory/createModel dir-name))

(defn close-model
  [model]
  (.close model))

(defn create-resource
  [model uri]
  (.createResource model uri))

(defn get-resource
  [model uri]
  (.getResource model uri))

(defn create-property
  [model name-space local-name]
  (.createProperty model name-space local-name))

(defn add-object
  [resource property object]
  (.addProperty resource property object))

(defn add-literal
  [resource property object]
  (.addLiteral resource property object))

(defn subject
  [stmt]
  (.getSubject stmt))

(defn predicate
  [stmt]
  (.getPredicate stmt))

(defn object
  [stmt]
  (.getObject stmt))

(defn triple
  [stmt]
  {:subject (subject stmt)
   :predicate (predicate stmt)
   :object (object stmt)})

(defn triples
  [stmt-iterator]
  (map triple (iterator-seq stmt-iterator)))

(defn properties
  ([resource]
     (triples (.listProperties resource)))
  ([resource property]
     (triples (.listProperties resource property))))

(defn list-statements
  [model s p o]
  (triples (.listStatements model s p o)))

(defn create-bag
  [model]
  (.createBag model))

(defn create-alt
  [model]
  (.createAlt model))

(defn create-seq
  [model]
  (.createSeq model))

(defn add
  [container stmt]
  (.add container stmt))

(defn container-contents
  [container]
  (triples (.iterator container)))

(defn- sparql-result-var
  [sparql-result var-name]
  [(keyword var-name) (.get sparql-result var-name)])

(defn- sparql-result-binding-to-map
  [sparql-result]
  (let [var-names (iterator-seq (.varNames sparql-result))]
    (into {} (map #(sparql-result-var sparql-result %)  var-names))))

(defn- sparql-results-to-seq
  [sparql-results]
  (map sparql-result-binding-to-map (iterator-seq sparql-results)))

(defn select-query
  [model q]
  (-> q
      (QueryFactory/create)
      (QueryExecutionFactory/create model)
      (.execSelect)
      (sparql-results-to-seq)))