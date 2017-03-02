(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('ConcoursDeleteController',ConcoursDeleteController);

    ConcoursDeleteController.$inject = ['$uibModalInstance', 'entity', 'Concours'];

    function ConcoursDeleteController($uibModalInstance, entity, Concours) {
        var vm = this;

        vm.concours = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Concours.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
