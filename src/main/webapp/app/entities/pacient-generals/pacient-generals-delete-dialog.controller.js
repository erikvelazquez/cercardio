(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientGeneralsDeleteController',PacientGeneralsDeleteController);

    PacientGeneralsDeleteController.$inject = ['$uibModalInstance', 'entity', 'PacientGenerals'];

    function PacientGeneralsDeleteController($uibModalInstance, entity, PacientGenerals) {
        var vm = this;

        vm.pacientGenerals = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PacientGenerals.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
