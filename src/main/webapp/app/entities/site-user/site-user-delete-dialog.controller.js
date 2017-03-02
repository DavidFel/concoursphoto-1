(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('SiteUserDeleteController',SiteUserDeleteController);

    SiteUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'SiteUser'];

    function SiteUserDeleteController($uibModalInstance, entity, SiteUser) {
        var vm = this;

        vm.siteUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SiteUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
