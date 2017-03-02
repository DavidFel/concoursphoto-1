(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('ThemeDeleteController',ThemeDeleteController);

    ThemeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Theme'];

    function ThemeDeleteController($uibModalInstance, entity, Theme) {
        var vm = this;

        vm.theme = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Theme.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
