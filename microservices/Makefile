MICROSERVICES=student lecture room

.PHONY: all
	
all:
	@mvn clean install
	@if [ ! -d "target" ]; then mkdir "target"; fi
	@$(foreach microservice,$(MICROSERVICES),cp "$(microservice)/target/$(microservice)-0.1.jar" ./target;)
	@echo "Copied all microservices to target folder"
