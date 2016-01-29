'use strict';

angular.module('rrappApp')
    .controller('FriendrequestDetailController', function ($scope, $rootScope, $stateParams, entity, Friendrequest, User) {
        $scope.friendrequest = entity;
        $scope.load = function (id) {
            Friendrequest.get({id: id}, function(result) {
                $scope.friendrequest = result;
            });
        };
        var unsubscribe = $rootScope.$on('rrappApp:friendrequestUpdate', function(event, result) {
            $scope.friendrequest = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
