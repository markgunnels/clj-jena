(ns clj-jena.model
  (:import [com.hp.hpl.jena.rdf.model
            Model
            Resource
            SimpleSelector
            ModelFactory]
           [com.hp.hpl.jena.query
            QueryFactory
            QueryExecutionFactory]
           [com.hp.hpl.jena.tdb
            TDBFactory]
           [com.hp.hpl.jena.ontology
            OntModelSpec]
           [com.hp.hpl.jena.reasoner
            ReasonerRegistry])
  (:require [clojure.java.io :as io]))

(defn default-model
  []
  (ModelFactory/createDefaultModel))

(defn get-tdb-model
  [dir-name]
  (TDBFactory/createModel dir-name))

(defn close-model
  [model]
  (.close model))

(defn open-resource-as-model
  [resource-name]
  (let [uri (io/resource resource-name)
        is (io/input-stream uri)
        m (default-model)]
    (.read m is nil)))

(defn create-rdfs-model
  [^Model schema ^Model model]
  (ModelFactory/createRDFSModel schema model))

(defn create-ontology-model
  [base-model]
  (ModelFactory/createOntologyModel (OntModelSpec/RDFS_MEM_RDFS_INF)
                                    base-model))

(defn list-namespaces
  [model]
  (iterator-seq (.listNameSpaces model)))

(defn prefix-map
  [model]
  (.getNsPrefixMap model))

;; TODO: No worky. No idea why.
;; (defn import-ontology-from-resource
;;   [model ontology-resource-name]
;;   (let [uri (io/resource ontology-resource-name)]
;;     (.read model uri)))


(defn create-abox-tbox-model
  [dir-name ontology-file-name]
  (let [a-box (get-tdb-model dir-name)
        t-box (open-resource-as-model "simple.rdf")
        rdfs-reasoner (ReasonerRegistry/getRDFSReasoner)
        rdfs-reasoner (.bindSchema rdfs-reasoner t-box)
        spec (OntModelSpec. (OntModelSpec/RDFS_MEM_RDFS_INF))
        _ (.setReasoner spec rdfs-reasoner)]
    (ModelFactory/createOntologyModel spec a-box)))