(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('CivilStatusDeleteController',CivilStatusDeleteController);

    CivilStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'CivilStatus'];

    function CivilStatusDeleteController($uibModalInstance, entity, CivilStatus) {
        var vm = this;

        vm.civilStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CivilStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
