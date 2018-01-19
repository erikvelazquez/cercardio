(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientAppDeleteController',PacientAppDeleteController);

    PacientAppDeleteController.$inject = ['$uibModalInstance', 'entity', 'PacientApp'];

    function PacientAppDeleteController($uibModalInstance, entity, PacientApp) {
        var vm = this;

        vm.pacientApp = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PacientApp.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
