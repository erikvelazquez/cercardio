(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('ProgramsDialogController', ProgramsDialogController);

    ProgramsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Programs'];

    function ProgramsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Programs) {
        var vm = this;

        vm.programs = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.programs.id !== null) {
                Programs.update(vm.programs, onSaveSuccess, onSaveError);
            } else {
                Programs.save(vm.programs, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:programsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
