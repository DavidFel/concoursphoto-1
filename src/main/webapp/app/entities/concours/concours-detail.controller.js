(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('ConcoursDetailController', ConcoursDetailController);

    ConcoursDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Concours', 'Theme'];

    function ConcoursDetailController($scope, $rootScope, $stateParams, previousState, entity, Concours, Theme) {
        var vm = this;

        vm.concours = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('concoursphotoApp:concoursUpdate', function(event, result) {
            vm.concours = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
