(ns twitter.api.restful-1-1
  (:use
   [twitter core callbacks api])
  (:import
   (twitter.api ApiContext)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def ^:dynamic *rest-api* (make-api-context "http" "api.twitter.com" 1.1))
(def ^:dynamic *oauth-api* (make-api-context "https" "api.twitter.com"))
(def ^:dynamic *rest-upload-api* (make-api-context "http" "upload.twitter.com" 1.1))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmacro def-twitter-restful-method
  "defines a synchronous, single method using the supplied api context

   NOTE: we rewrite this helper macro here since the original is bound to the 1.0 API"
  [name action resource-path & rest]

  
  `(def-twitter-method ~name ~action ~resource-path :api ~*rest-api* :callbacks (get-default-callbacks :sync :single) ~@rest))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Accounts
(def-twitter-restful-method verify-credentials :get "account/verify_credentials.json")
(def-twitter-restful-method account-settings :get "account/settings.json")
(def-twitter-restful-method update-account-settings :post "account/settings.json") 
(def-twitter-restful-method update-profile-colors :post "account/update_profile_colors.json")
(def-twitter-restful-method update-profile-image :post "account/update_profile_image.json")
(def-twitter-restful-method update-profile-background-image :post "account/update_profile_background_image.json")
(def-twitter-restful-method update-profile :post "account/update_profile.json")

;; Blocks
(def-twitter-restful-method create-block :post "blocks/create.json")
(def-twitter-restful-method destroy-block :post "blocks/destroy.json")
(def-twitter-restful-method blocking-users :get "blocks/list.json")
(def-twitter-restful-method blocking-user-ids :get "blocks/ids.json")

;; Timeline
(def-twitter-restful-method home-timeline :get "statuses/home_timeline.json")
(def-twitter-restful-method user-timeline :get "statuses/user_timeline.json")
(def-twitter-restful-method mentions-timeline :get "statuses/mentions_timeline.json")

;; Statuses
(def-twitter-restful-method show-status :get "statuses/show/{:id}.json")
(def-twitter-restful-method update-status :post "statuses/update.json")
(def-twitter-restful-method destroy-status :post "statuses/destroy/{:id}.json")
(def-twitter-restful-method retweet-status :post "statuses/retweet/{:id}.json")
(def-twitter-restful-method show-retweets :get "statuses/retweets/{:id}.json")
; Supply the status and file to the :body as a sequence using the functions 'file-body-part' and 'status-body-part'
; i.e. :body [(file-body-part "/pics/mypic.jpg") (status-body-part "hello world")]
; for an example, see twitter.test.file-upload
(def-twitter-restful-method update-with-media :post "statuses/update_with_media.json"
  :api *rest-upload-api*
  :headers {:content-type "multipart/form-data"})
(def-twitter-restful-method status-oembed :get "statuses/oembed.json")

;; User
(def-twitter-restful-method show-user :get "users/show.json")
(def-twitter-restful-method lookup-users :get "users/lookup.json")
(def-twitter-restful-method search-users :get "users/search.json")
(def-twitter-restful-method suggest-slugs :get "users/suggestions.json")
(def-twitter-restful-method suggest-users-for-slug :get "users/suggestions/{:slug}.json")
(def-twitter-restful-method suggest-users-for-slug-members :get "users/suggestions/{:slug}/members.json")
(def-twitter-restful-method show-contributors :get "users/contributors.json")
(def-twitter-restful-method show-contributees :get "users/contributees.json")

;; Trends
(def-twitter-restful-method place-trends :get "trends/place.json")
(def-twitter-restful-method available-trends :get "trends/available.json")
(def-twitter-restful-method closest-trends :get "trends/closest.json")

;; Oauth
(def-twitter-restful-method oauth-authenticate :get "oauth/authenticate" :api *oauth-api*)
(def-twitter-restful-method oauth-authorize :get "oauth/authorize" :api *oauth-api*)
(def-twitter-restful-method oauth-access-token :post "oauth/access_token" :api *oauth-api*)
(def-twitter-restful-method oauth-request-token :post "oauth/request_token" :api *oauth-api*)

;; Lists
(def-twitter-restful-method show-lists :get "lists/list.json")
(def-twitter-restful-method show-list :get "lists/show.json")
(def-twitter-restful-method list-memberships :get "lists/memberships.json")
(def-twitter-restful-method list-subscriptions :get "lists/subscriptions.json")
(def-twitter-restful-method list-statuses :get "lists/statuses.json")
(def-twitter-restful-method create-list :post "lists/create.json")
(def-twitter-restful-method update-list :post "lists/update.json")
(def-twitter-restful-method destroy-list :post "lists/destroy.json")

;; List members
(def-twitter-restful-method list-members :get "lists/members.json")
(def-twitter-restful-method show-member :get "lists/members/show.json")
(def-twitter-restful-method add-member :post "lists/members/create.json")
(def-twitter-restful-method add-members :post "lists/members/create_all.json")
(def-twitter-restful-method remove-member :post "lists/members/destroy.json")
(def-twitter-restful-method remove-members :post "lists/members/destroy_all.json")

;; List subscribers
(def-twitter-restful-method list-subscribers :get "lists/subscribers.json")
(def-twitter-restful-method show-subscriber :get "lists/subscribers/show.json")
(def-twitter-restful-method add-subscriber :post "lists/subscribers/create.json")
(def-twitter-restful-method remove-subscriber :post "lists/subscribers/destroy.json")

;; Direct messages
(def-twitter-restful-method direct-messages :get "direct_messages.json")
(def-twitter-restful-method sent-messages :get "direct_messages/sent.json")
(def-twitter-restful-method show-message :get "direct_messages/show.json")
(def-twitter-restful-method send-message :post "direct_messages/new.json")
(def-twitter-restful-method destroy-message :post "direct_messages/destroy.json")

;; Friendships
(def-twitter-restful-method create-friendship :post "friendships/create.json")
(def-twitter-restful-method update-friendship :post "friendships/update.json")
(def-twitter-restful-method destroy-friendship :post "friendships/destroy.json")
(def-twitter-restful-method lookup-friendship :get "friendships/lookup.json")
(def-twitter-restful-method show-friendship :get "friendships/show.json")
(def-twitter-restful-method incoming-friendship :get "friendships/incoming.json")
(def-twitter-restful-method outgoing-friendship :get "friendships/outgoing.json")

;; Friends and followers
(def-twitter-restful-method show-friends :get "friends/ids.json")
(def-twitter-restful-method show-followers :get "followers/ids.json")

;; Favourites
(def-twitter-restful-method favourites :get "favorites/list.json")
(def-twitter-restful-method create-favourite :post "favorites/create.json")
(def-twitter-restful-method destroy-favourite :post "favorites/destroy.json")

;; Report spam
(def-twitter-restful-method report-spam :post "report_spam.json")

;; Saved searches
(def-twitter-restful-method saved-searches :get "saved_searches/list.json")
(def-twitter-restful-method show-saved-search :get "saved_searches/show/{:id}.json")
(def-twitter-restful-method create-saved-search :post "saved_searches/create.json")
(def-twitter-restful-method destroy-saved-search :post "saved_searches/destroy/{:id}.json")

;; Geo
(def-twitter-restful-method search-places :get "geo/search.json")
(def-twitter-restful-method similar-places :get "geo/similar_places.json")
(def-twitter-restful-method reverse-geocode :get "geo/reverse_geocode.json")
(def-twitter-restful-method show-place :get "geo/id/{:place_id}.json")
(def-twitter-restful-method create-place :post "geo/place.json")

;; Help
(def-twitter-restful-method legal-tos :get "help/tos.json")
(def-twitter-restful-method legal-privacy :get "help/privacy.json")
(def-twitter-restful-method help-configuration :get "help/configuration.json")
(def-twitter-restful-method help-languages :get "help/languages.json")

;; Application
(def-twitter-restful-method rate-limit-status :get "application/rate_limit_status.json")

;; Search
(def-twitter-restful-method search-tweets :get "search/tweets.json")