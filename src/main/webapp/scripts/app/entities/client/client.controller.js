'use strict';

angular.module('rrappApp')
    .controller('ClientController', function ($scope, $state, Client) {

        $scope.clients = [];
        $scope.loadAll = function() {
            Client.query(function(result) {
               $scope.clients = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.client = {
                firstname: null,
                lastname: null,
                e_mail: null,
                date_of_birth: null,
                id: null
            };
        };
    });
