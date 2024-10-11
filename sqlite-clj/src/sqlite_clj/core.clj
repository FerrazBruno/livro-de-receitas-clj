(ns sqlite-clj.core
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [next.jdbc.result-set :as rs]))


(def db-spec
  {:dbtype "sqlite"
   :dbname "database.db"})


(def ds (jdbc/get-datasource db-spec))


(def cria-tabela-qry
  (str " CREATE TABLE IF NOT EXISTS usuarios( "
       "   id INTEGER PRIMARY KEY AUTOINCREMENT, "
       "   idade INTEGER) "))


(def select-qry
  " SELECT * FROM usuarios ")


(defn criar-tabela [qry]
  (jdbc/execute! ds [qry]))


(defn insere-usuario [nome idade]
  (sql/insert! ds :usuarios {:nome nome :idade idade}))


(defn get-usuarios [qry]
  (sql/query ds [qry] {:builder-fn rs/as-unqualified-maps}))


(defn run [opts]
  (println "Criando tabela...")
  (criar-tabela cria-tabela-qry)
  (println "Inserindo usuários...")
  (insere-usuario "Frodo" 30)
  (insere-usuario "Sam" 25)
  (println "Usuários inseridos:")
  (doseq [user (get-usuarios select-qry)]
    (println user)))
