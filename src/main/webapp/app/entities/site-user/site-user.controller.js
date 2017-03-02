(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('SiteUserController', SiteUserController);

    SiteUserController.$inject = ['$scope', '$state', 'SiteUser'];

    function SiteUserController ($scope, $state, SiteUser) {
        var vm = this;

        vm.siteUsers = [];

        loadAll();

        function loadAll() {
            SiteUser.query(function(result) {
                vm.siteUsers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
