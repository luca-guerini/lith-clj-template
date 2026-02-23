(ns {{top/ns}}.{{main/ns}}.core
  (:gen-class
   :name    {{top/ns}}.{{main/file}}.Plugin
   :extends org.bukkit.plugin.java.JavaPlugin))

(defn -onEnable [this]
  (.info (.getLogger this) "{{main}} enabled."))

(defn -onDisable [this]
  (.info (.getLogger this) "{{main}} disabled."))
