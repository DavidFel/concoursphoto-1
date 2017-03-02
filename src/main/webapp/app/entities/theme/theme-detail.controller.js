(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('ThemeDetailController', ThemeDetailController);

    ThemeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Theme', 'Concours'];

    function ThemeDetailController($scope, $rootScope, $stateParams, previousState, entity, Theme, Concours) {
        var vm = this;

        vm.theme = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('concoursphotoApp:themeUpdate', function(event, result) {
            vm.theme = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
