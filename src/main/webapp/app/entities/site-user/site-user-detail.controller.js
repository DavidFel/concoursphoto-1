(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('SiteUserDetailController', SiteUserDetailController);

    SiteUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SiteUser'];

    function SiteUserDetailController($scope, $rootScope, $stateParams, previousState, entity, SiteUser) {
        var vm = this;

        vm.siteUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('concoursphotoApp:siteUserUpdate', function(event, result) {
            vm.siteUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
