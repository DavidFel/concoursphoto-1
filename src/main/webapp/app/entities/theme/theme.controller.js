(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('ThemeController', ThemeController);

    ThemeController.$inject = ['$scope', '$state', 'Theme'];

    function ThemeController ($scope, $state, Theme) {
        var vm = this;

        vm.themes = [];

        loadAll();

        function loadAll() {
            Theme.query(function(result) {
                vm.themes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
