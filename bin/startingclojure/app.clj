(ns startingclojure.app
  (:use [compojure handler
         [core :only [GET POST defroutes]]])
  (:require [net.cgrand.enlive-html :as en] 
            [ring.util.response :as response] 
            [ring.adapter.jetty :as jetty]))

(defonce counter (atom 10000))

(defonce urls (atom {}))

(defn shorten
  [url]
  (let [id (swap! counter inc)
        id (Long/toString id 36)]
    (swap! urls assoc id url)
    id))

(en/deftemplate homepage
  (en/xml-resource "homepage.html")
  [request])

(defn redirect
  [id]
  (response/redirect (@urls id)))

(defroutes app
  (GET "/" request (homepage request))
  (GET "/:id" [id] (redirect id)))