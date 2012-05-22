(ns clj-jena.ontology
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
            OntModelSpec])
  (:require [clojure.java.io :as io]))

(defn list-classes
  [ont-model]
  (iterator-seq (.listClasses ont-model)))

(defn uri
  [ont-class]
  (.getURI ont-class))

(defn get-ontology-class
  [ont-model uri]
  (.getOntClass ont-model uri))

(defn create-individual
  [ont-class uri]
  (.createIndividual ont-class uri))

(defn properties-of-class
  [ont-class]
  (iterator-seq (.listDeclaredProperties ont-class)))

(defn add-literal-to-individual
  [i property value]
  (.addLiteral i property value))

(defn add-property-to-individual
  [i property rdf-node]
  (.addProperty i property rdf-node))

