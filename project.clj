(defproject prismatic/schema-generators "0.1.0-SNAPSHOT"
  :description "Clojure(Script) library for data generation from schemas"
  :url "http://github.com/plumatic/schema-generators"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/test.check "0.9.0"]]

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.8.0"]
                                  [org.clojure/clojurescript "1.7.228"]
                                  [org.clojure/tools.nrepl "0.2.5"]
                                  [prismatic/schema "1.0.5"]]
                   :plugins [[com.keminglabs/cljx "0.6.0" :exclusions [org.clojure/clojure]]
                             [codox "0.8.8"]
                             [lein-cljsbuild "1.0.5"]
                             [com.cemerick/clojurescript.test "0.3.1"]]
                   :cljx {:builds [{:source-paths ["src"]
                                    :output-path "target/generated/src/clj"
                                    :rules :clj}
                                   {:source-paths ["src"]
                                    :output-path "target/generated/src/cljs"
                                    :rules :cljs}
                                   {:source-paths ["test"]
                                    :output-path "target/generated/test/clj"
                                    :rules :clj}
                                   {:source-paths ["test"]
                                    :output-path "target/generated/test/cljs"
                                    :rules :cljs}]}}
             :1.7 {:dependencies [[org.clojure/clojure "1.7.0"]]}}

  :aliases {"all" ["with-profile" "dev:dev,1.7"]
            "deploy" ["do" "clean," "cljx" "once," "deploy" "clojars"]
            "test" ["do" "clean," "cljx" "once," "test," "with-profile" "dev" "cljsbuild" "test"]}

  :jar-exclusions [#"\.cljx|\.swp|\.swo|\.DS_Store"]

  :lein-release {:deploy-via :shell
                 :shell ["lein" "deploy"]}

  :auto-clean false

  :source-paths ["target/generated/src/clj" "src"]

  :resource-paths ["target/generated/src/cljs"]

  :test-paths ["target/generated/test/clj" "test"]

  :cljsbuild {:test-commands {"unit" ["phantomjs" :runner
                                      "this.literal_js_was_evaluated=true"
                                      "target/unit-test.js"]
                              "unit-no-assert" ["phantomjs" :runner
                                                "this.literal_js_was_evaluated=true"
                                                "target/unit-test-no-assert.js"]}
              :builds
              {:dev {:source-paths ["src/clj" "target/generated/src/cljs"]
                     :compiler {:output-to "target/main.js"
                                :optimizations :whitespace
                                :pretty-print true}}
               :test {:source-paths ["src/clj" "test/clj"
                                     "target/generated/src/cljs"
                                     "target/generated/test/cljs"]
                      :compiler {:output-to "target/unit-test.js"
                                 :optimizations :whitespace
                                 :pretty-print true}}
               :test-no-assert {:source-paths ["src/clj" "test/clj"
                                               "target/generated/src/cljs"
                                               "target/generated/test/cljs"]
                                :assert false
                                :compiler {:output-to "target/unit-test-no-assert.js"
                                           :optimizations :whitespace
                                           :pretty-print true}}}}

  :codox {:src-uri-mapping {#"target/generated/src/clj" #(str "src/cljx/" % "x")}
          :src-dir-uri "http://github.com/plumatic/schema-generators/blob/master/"
          :src-linenum-anchor-prefix "L"}

  :signing {:gpg-key "66E0BF75"})