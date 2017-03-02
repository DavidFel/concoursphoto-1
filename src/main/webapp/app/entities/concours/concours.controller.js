(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('ConcoursController', ConcoursController);

    ConcoursController.$inject = ['$scope', '$state', 'Concours'];

    function ConcoursController ($scope, $state, Concours) {
        var vm = this;

        vm.concours = [];

        loadAll();

        function loadAll() {
            Concours.query(function(result) {
                vm.concours = result;
                vm.searchQuery = null;
            });
        }
    }
})();
