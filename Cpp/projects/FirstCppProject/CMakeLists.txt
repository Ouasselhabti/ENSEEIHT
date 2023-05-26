cmake_minimum_required(VERSION 3.22.1)
project(MyProject)
add_executable(${PROJECT_NAME} main.cpp)
install(TARGETS ${PROJECT_NAME} DESTINATION bin)
