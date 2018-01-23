package main

import (
	"encoding/json"
	"log"
	"net/http"

	_ "github.com/go-sql-driver/mysql"
	"github.com/gorilla/handlers"
	"github.com/gorilla/mux"
	"github.com/jinzhu/gorm"
)

var (
	db *gorm.DB
)

//Room struct for the Room-Microservice
type Room struct {
	ID   int    `json:"id"`
	Name string `json:"name"`
}

//TableName changes table name from rooms to room
func (Room) TableName() string {
	return "room"
}

func main() {
	var err error
	db, err = gorm.Open("mysql", "root:@/hochschule?charset=utf8&parseTime=True&loc=Local")
	defer db.Close()
	if err != nil {
		log.Fatal(err)
	}
	headersOk := handlers.AllowedHeaders([]string{"X-Requested-With", "Content-Type", "Accept"})
	originsOk := handlers.AllowedOrigins([]string{"*"})
	methodsOk := handlers.AllowedMethods([]string{"GET", "HEAD", "POST", "PUT", "OPTIONS", "DELETE"})

	router := mux.NewRouter()
	router.HandleFunc("/rooms", getRooms).Methods("GET")
	router.HandleFunc("/rooms", createRoom).Methods("POST")
	router.HandleFunc("/rooms/{id}", deleteRoom).Methods("DELETE")
	log.Fatal(http.ListenAndServe(":8080", handlers.CORS(headersOk, originsOk, methodsOk)(router)))
}

func getRooms(w http.ResponseWriter, r *http.Request) {
	var rooms []Room
	db.Find(&rooms)
	json.NewEncoder(w).Encode(&rooms)
	log.Printf("getRooms called")
}

func createRoom(w http.ResponseWriter, r *http.Request) {
	var room Room
	_ = json.NewDecoder(r.Body).Decode(&room)
	if err := db.Create(&room).Error; err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		log.Print(err)
	} else {
		w.WriteHeader(http.StatusCreated)
	}
	log.Printf("create room called")
}

func deleteRoom(w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)
	id := params["id"]
	log.Printf("delete room called with id %s", id)
	if err := db.Where("id = ? ", id).Delete(Room{}).Error; err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		log.Print(err)
	}
}
