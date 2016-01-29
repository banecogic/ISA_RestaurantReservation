'use strict';

angular.module('rrappApp')
    .controller('FriendrequestController', function ($scope, $state, Friendrequest) {

        $scope.friendrequests = [];
        $scope.loadAll = function() {
            Friendrequest.query(function(result) {
               $scope.friendrequests = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.friendrequest = {
                sentDateAndTime: null,
                isAccepted: false,
                acceptedDateAndTime: null,
                id: null
            };
        };
    });
