(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DisabilitiesDeleteController',DisabilitiesDeleteController);

    DisabilitiesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Disabilities'];

    function DisabilitiesDeleteController($uibModalInstance, entity, Disabilities) {
        var vm = this;

        vm.disabilities = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Disabilities.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
